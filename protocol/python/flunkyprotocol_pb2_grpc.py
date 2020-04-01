# Generated by the gRPC Python protocol compiler plugin. DO NOT EDIT!
import grpc

import flunkyprotocol_pb2 as flunkyprotocol__pb2


class SimulatorStub(object):
  """A simple Flunkyball API.

  The API manages throws and teams. Teams contain players.
  """

  def __init__(self, channel):
    """Constructor.

    Args:
      channel: A grpc.Channel.
    """
    self.Throw = channel.unary_unary(
        '/endpoints.flunky.simulator.Simulator/Throw',
        request_serializer=flunkyprotocol__pb2.ThrowReq.SerializeToString,
        response_deserializer=flunkyprotocol__pb2.ThrowResp.FromString,
        )
    self.RegisterPlayer = channel.unary_unary(
        '/endpoints.flunky.simulator.Simulator/RegisterPlayer',
        request_serializer=flunkyprotocol__pb2.RegisterPlayerReq.SerializeToString,
        response_deserializer=flunkyprotocol__pb2.RegisterPlayerResp.FromString,
        )
    self.KickPlayer = channel.unary_unary(
        '/endpoints.flunky.simulator.Simulator/KickPlayer',
        request_serializer=flunkyprotocol__pb2.KickPlayerReq.SerializeToString,
        response_deserializer=flunkyprotocol__pb2.KickPlayerResp.FromString,
        )
    self.ResetGame = channel.unary_unary(
        '/endpoints.flunky.simulator.Simulator/ResetGame',
        request_serializer=flunkyprotocol__pb2.ResetGameReq.SerializeToString,
        response_deserializer=flunkyprotocol__pb2.ResetGameResp.FromString,
        )
    self.SelectThrowingPlayer = channel.unary_unary(
        '/endpoints.flunky.simulator.Simulator/SelectThrowingPlayer',
        request_serializer=flunkyprotocol__pb2.SelectThrowingPlayerReq.SerializeToString,
        response_deserializer=flunkyprotocol__pb2.SelectThrowingPlayerResp.FromString,
        )
    self.SendMessage = channel.unary_unary(
        '/endpoints.flunky.simulator.Simulator/SendMessage',
        request_serializer=flunkyprotocol__pb2.SendMessageReq.SerializeToString,
        response_deserializer=flunkyprotocol__pb2.SendMessageResp.FromString,
        )
    self.StreamState = channel.unary_stream(
        '/endpoints.flunky.simulator.Simulator/StreamState',
        request_serializer=flunkyprotocol__pb2.StreamStateReq.SerializeToString,
        response_deserializer=flunkyprotocol__pb2.StreamStateResp.FromString,
        )
    self.StreamEvents = channel.unary_stream(
        '/endpoints.flunky.simulator.Simulator/StreamEvents',
        request_serializer=flunkyprotocol__pb2.StreamEventsReq.SerializeToString,
        response_deserializer=flunkyprotocol__pb2.StreamEventsResp.FromString,
        )
    self.StreamLog = channel.unary_stream(
        '/endpoints.flunky.simulator.Simulator/StreamLog',
        request_serializer=flunkyprotocol__pb2.LogReq.SerializeToString,
        response_deserializer=flunkyprotocol__pb2.LogResp.FromString,
        )


class SimulatorServicer(object):
  """A simple Flunkyball API.

  The API manages throws and teams. Teams contain players.
  """

  def Throw(self, request, context):
    # missing associated documentation comment in .proto file
    pass
    context.set_code(grpc.StatusCode.UNIMPLEMENTED)
    context.set_details('Method not implemented!')
    raise NotImplementedError('Method not implemented!')

  def RegisterPlayer(self, request, context):
    # missing associated documentation comment in .proto file
    pass
    context.set_code(grpc.StatusCode.UNIMPLEMENTED)
    context.set_details('Method not implemented!')
    raise NotImplementedError('Method not implemented!')

  def KickPlayer(self, request, context):
    # missing associated documentation comment in .proto file
    pass
    context.set_code(grpc.StatusCode.UNIMPLEMENTED)
    context.set_details('Method not implemented!')
    raise NotImplementedError('Method not implemented!')

  def ResetGame(self, request, context):
    """resets Strafbier counter, Abgaben, shuffles teams 
    and plays prepare game clip afterwards
    """
    context.set_code(grpc.StatusCode.UNIMPLEMENTED)
    context.set_details('Method not implemented!')
    raise NotImplementedError('Method not implemented!')

  def SelectThrowingPlayer(self, request, context):
    # missing associated documentation comment in .proto file
    pass
    context.set_code(grpc.StatusCode.UNIMPLEMENTED)
    context.set_details('Method not implemented!')
    raise NotImplementedError('Method not implemented!')

  def SendMessage(self, request, context):
    # missing associated documentation comment in .proto file
    pass
    context.set_code(grpc.StatusCode.UNIMPLEMENTED)
    context.set_details('Method not implemented!')
    raise NotImplementedError('Method not implemented!')

  def StreamState(self, request, context):
    # missing associated documentation comment in .proto file
    pass
    context.set_code(grpc.StatusCode.UNIMPLEMENTED)
    context.set_details('Method not implemented!')
    raise NotImplementedError('Method not implemented!')

  def StreamEvents(self, request, context):
    # missing associated documentation comment in .proto file
    pass
    context.set_code(grpc.StatusCode.UNIMPLEMENTED)
    context.set_details('Method not implemented!')
    raise NotImplementedError('Method not implemented!')

  def StreamLog(self, request, context):
    # missing associated documentation comment in .proto file
    pass
    context.set_code(grpc.StatusCode.UNIMPLEMENTED)
    context.set_details('Method not implemented!')
    raise NotImplementedError('Method not implemented!')


def add_SimulatorServicer_to_server(servicer, server):
  rpc_method_handlers = {
      'Throw': grpc.unary_unary_rpc_method_handler(
          servicer.Throw,
          request_deserializer=flunkyprotocol__pb2.ThrowReq.FromString,
          response_serializer=flunkyprotocol__pb2.ThrowResp.SerializeToString,
      ),
      'RegisterPlayer': grpc.unary_unary_rpc_method_handler(
          servicer.RegisterPlayer,
          request_deserializer=flunkyprotocol__pb2.RegisterPlayerReq.FromString,
          response_serializer=flunkyprotocol__pb2.RegisterPlayerResp.SerializeToString,
      ),
      'KickPlayer': grpc.unary_unary_rpc_method_handler(
          servicer.KickPlayer,
          request_deserializer=flunkyprotocol__pb2.KickPlayerReq.FromString,
          response_serializer=flunkyprotocol__pb2.KickPlayerResp.SerializeToString,
      ),
      'ResetGame': grpc.unary_unary_rpc_method_handler(
          servicer.ResetGame,
          request_deserializer=flunkyprotocol__pb2.ResetGameReq.FromString,
          response_serializer=flunkyprotocol__pb2.ResetGameResp.SerializeToString,
      ),
      'SelectThrowingPlayer': grpc.unary_unary_rpc_method_handler(
          servicer.SelectThrowingPlayer,
          request_deserializer=flunkyprotocol__pb2.SelectThrowingPlayerReq.FromString,
          response_serializer=flunkyprotocol__pb2.SelectThrowingPlayerResp.SerializeToString,
      ),
      'SendMessage': grpc.unary_unary_rpc_method_handler(
          servicer.SendMessage,
          request_deserializer=flunkyprotocol__pb2.SendMessageReq.FromString,
          response_serializer=flunkyprotocol__pb2.SendMessageResp.SerializeToString,
      ),
      'StreamState': grpc.unary_stream_rpc_method_handler(
          servicer.StreamState,
          request_deserializer=flunkyprotocol__pb2.StreamStateReq.FromString,
          response_serializer=flunkyprotocol__pb2.StreamStateResp.SerializeToString,
      ),
      'StreamEvents': grpc.unary_stream_rpc_method_handler(
          servicer.StreamEvents,
          request_deserializer=flunkyprotocol__pb2.StreamEventsReq.FromString,
          response_serializer=flunkyprotocol__pb2.StreamEventsResp.SerializeToString,
      ),
      'StreamLog': grpc.unary_stream_rpc_method_handler(
          servicer.StreamLog,
          request_deserializer=flunkyprotocol__pb2.LogReq.FromString,
          response_serializer=flunkyprotocol__pb2.LogResp.SerializeToString,
      ),
  }
  generic_handler = grpc.method_handlers_generic_handler(
      'endpoints.flunky.simulator.Simulator', rpc_method_handlers)
  server.add_generic_rpc_handlers((generic_handler,))