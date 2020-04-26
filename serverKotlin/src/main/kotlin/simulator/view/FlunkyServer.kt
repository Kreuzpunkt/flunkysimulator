package simulator.view

import com.google.protobuf.Any.pack
import com.google.rpc.Code
import com.google.rpc.Status
import com.google.rpc.StatusProto
import de.flunkyteam.endpoints.projects.simulator.*
import io.grpc.StatusRuntimeException
import io.grpc.protobuf.StatusProto.toStatusRuntimeException
import io.grpc.stub.StreamObserver
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import simulator.DeactiveableHandler
import simulator.control.*
import simulator.control.VideoEvent

class FlunkyServer(
    private val gameController: GameController,
    private val messageController: MessageController,
    private val videoController: VideoController
) : SimulatorGrpc.SimulatorImplBase() {

    override fun throw_(request: ThrowReq?, responseObserver: StreamObserver<ThrowResp>?) {

        GlobalScope.launch {
            if (gameController.throwBall(request!!.playerName, request.strength))
                messageController.sendMessage(request.playerName, "hat ${request.strength} geworfen")
            else
                messageController.sendMessage(request.playerName, "darf nicht werfen")
        }
        responseObserver?.onNext(ThrowResp.getDefaultInstance())
        responseObserver?.onCompleted()
    }

    override fun registerPlayer(request: RegisterPlayerReq?, responseObserver: StreamObserver<RegisterPlayerResp>) {
        val name = request!!.playerName

        try {
            gameController.registerPlayer(name)
            messageController.sendMessage(request.playerName, "hat sich registriert. Willkommen Athlet!")
            responseObserver.onNext(RegisterPlayerResp.getDefaultInstance())
        } catch (le: LoginError) {
            val status: Status = Status.newBuilder()
                .setCode(Code.INVALID_ARGUMENT.number)
                .setMessage("Name or secret wrong")
                .addDetails(pack(le.toGRPC()))
                .build()
            responseObserver.onError(toStatusRuntimeException(status))
        }

        responseObserver.onCompleted()
    }

    override fun kickPlayer(request: KickPlayerReq?, responseObserver: StreamObserver<KickPlayerResp>?) {
        val name = request!!.targetName

        if (gameController.removePlayer(name))
            messageController.sendMessage(request.playerName, "hat ${name} rausgeworfen.")
        else
            messageController.sendMessage(request.playerName, "konnte ${name} nicht rauswerfen.")

        responseObserver?.onNext(KickPlayerResp.getDefaultInstance())
        responseObserver?.onCompleted()
    }

    override fun switchTeam(request: SwitchTeamReq?, responseObserver: StreamObserver<SwitchTeamResp>?) {
        val name = request!!.targetName
        val team = request.targetTeam



        if (gameController.switchTeam(name, team))
            messageController.sendMessage(request.playerName, "hat $name nach ${team.toString()} verschoben.")
        else
            messageController.sendMessage(request.playerName, "konnte ${name} nicht verschieben.")

        responseObserver?.onNext(SwitchTeamResp.getDefaultInstance())
        responseObserver?.onCompleted()
    }

    override fun modifyStrafbierCount(
        request: ModifyStrafbierCountReq?,
        responseObserver: StreamObserver<ModifyStrafbierCountResp>?
    ) {
        if (gameController.modifyStrafbierCount(request!!.targetTeam, request.increment)) {
            val text = "hat ein Strafbier für ${request.targetTeam} " +
                    if (request.increment)
                        "hinzugefügt"
                    else
                        "entfernt"
            messageController.sendMessage(request.playerName, text)
        } else {
            messageController.sendMessage(
                request.playerName,
                " hat die Strafbiere für ${request.targetTeam} nicht verändert."
            )
        }

        responseObserver?.onNext(ModifyStrafbierCountResp.getDefaultInstance())
        responseObserver?.onCompleted()
    }

    override fun resetGame(request: ResetGameReq?, responseObserver: StreamObserver<ResetGameResp>?) {

        if (gameController.resetGameAndShuffleTeams())
            messageController.sendMessage(
                request!!.playerName,
                "den Ground neu ausgemessen, die Kreide nachgezeichnet, die Teams gemischt, die Center nachgefüllt und den Ball aufgepumt."
            )
        else
            messageController.sendMessage(request!!.playerName, "konnte das Spiel nicht neustarten")

        responseObserver?.onNext(ResetGameResp.getDefaultInstance())
        responseObserver?.onCompleted()
    }

    override fun selectThrowingPlayer(
        request: SelectThrowingPlayerReq?,
        responseObserver: StreamObserver<SelectThrowingPlayerResp>?
    ) {
        if (gameController.forceThrowingPlayer(request!!.targetName))
            messageController.sendMessage(
                request.playerName,
                "hat ${request.targetName} als werfenden Spieler festgelegt."
            )
        else
            messageController.sendMessage(
                request.playerName,
                "konnte ${request.targetName} nicht als Werfer festlegen. Spielt nicht mit oder nicht existent?"
            )

        responseObserver?.onNext(SelectThrowingPlayerResp.getDefaultInstance())
        responseObserver?.onCompleted()
    }

    override fun abgegeben(request: AbgegebenReq?, responseObserver: StreamObserver<AbgegebenResp>?) {
        if (gameController.setAbgegeben(request!!.targetName, request.setTo)) {
            val text =
                "hat ${request.targetName}" + if (request.setTo) "s abgabe abgenommen." else " ein Bier geöffnet."
            messageController.sendMessage(request.playerName, text)
        } else
            messageController.sendMessage(
                request.playerName,
                "konnte ${request.targetName} Abgabestatus nicht ändern."
            )

        responseObserver?.onNext(AbgegebenResp.getDefaultInstance())
        responseObserver?.onCompleted()
    }

    override fun sendMessage(request: SendMessageReq?, responseObserver: StreamObserver<SendMessageResp>?) {
        messageController.sendMessage(request!!.playerName, request.content)

        responseObserver?.onNext(SendMessageResp.getDefaultInstance())
        responseObserver?.onCompleted()
    }

    override fun streamState(request: StreamStateReq?, responseObserver: StreamObserver<StreamStateResp>?) {
        // output current state
        responseObserver?.onNext(
            StreamStateResp.newBuilder()
                .setState(gameController.gameState.toGRPC())
                .build()
        )

        // output future states
        val handler =
            buildRegisterHandler { event: GameController.GameStateEvent ->
                //fails if stream is closed
                responseObserver?.onNext(
                    StreamStateResp.newBuilder()
                        .setState(event.state.toGRPC())
                        .build()
                )
            }

        gameController.addEventHandler(handler::doAction)
    }

    override fun streamVideoEvents(
        request: StreamVideoEventsReq?,
        responseObserver: StreamObserver<StreamVideoEventsResp>?
    ) {

        videoController.getAllPreparedVideoEvents().forEach {
            responseObserver?.onNext(
                StreamVideoEventsResp.newBuilder()
                    .setEvent(it.toGrpc())
                    .build()
            )
        }

        val handler =
            buildRegisterHandler { event: VideoEvent ->
                responseObserver?.onNext(
                    StreamVideoEventsResp.newBuilder()
                        .setEvent(event.toGrpc())
                        .build()
                )
            }

        videoController.addEventHandler(handler::doAction)
    }

    override fun streamLog(request: LogReq?, responseObserver: StreamObserver<LogResp>?) {

        val handler =
            buildRegisterHandler { event: MessageController.MessageEvent ->
                responseObserver?.onNext(
                    LogResp.newBuilder()
                        .setContent(event.content)
                        .build()
                )
            }

        messageController.addEventHandler(handler::doAction)
    }

    /***
     * action should put something in a responseObserver
     */
    private fun <Event> buildRegisterHandler(action: (Event) -> Unit): DeactiveableHandler<Event> =
        DeactiveableHandler({ event: Event,
                              deactiveableHandler: DeactiveableHandler<Event> ->
            try {
                //fails if stream is closed
                action(event)
            } catch (e: StatusRuntimeException) {
                if (e.status.code == io.grpc.Status.Code.CANCELLED) {
                    println("Another stream bites the dust. Message: \n ${e.message}")
                    deactiveableHandler.enabled = false
                    /*TODO delete handlers when connection gone but not while iterating
                         through handlers like in this position, because this would casue
                         concurrency modification errors because of the underlying HashSet
                         in the Event plugin.
                         handler?.let { gameController.removeEventHandler(it) }
                         */
                } else
                    throw e
            }
        })
}