package simulator.control

import de.flunkyteam.endpoints.projects.simulator.EnumLoginErrorReason
import de.flunkyteam.endpoints.projects.simulator.RegisterPlayerError

class LoginError(val loginErrorReason: EnumLoginErrorReason) : IllegalArgumentException(){
    fun toGRPC() = RegisterPlayerError
        .newBuilder()
        .setReason(this.loginErrorReason)
        .build()
}