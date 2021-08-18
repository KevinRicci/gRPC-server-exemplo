package br.com.zupacademy.frete

import br.com.zupacademy.CepRequest
import br.com.zupacademy.CepResponse
import br.com.zupacademy.ErrorDetails
import br.com.zupacademy.FreteServiceGrpc
import com.google.protobuf.Any
import com.google.rpc.Code
import io.grpc.Status
import io.grpc.stub.StreamObserver
import java.lang.IllegalArgumentException
import javax.inject.Singleton
import kotlin.random.Random

@Singleton
class Frete : FreteServiceGrpc.FreteServiceImplBase() {

    override fun consultaCep(request: CepRequest?, responseObserver: StreamObserver<CepResponse>?) {
        val cep = request?.cep

        if(cep.isNullOrBlank()){
            responseObserver!!.onError(Status.INVALID_ARGUMENT
                .withDescription("cep não pode ser vazio")
                .augmentDescription("formato esperado é 99999-999")
                .asRuntimeException())
            return
        }

        if(cep.endsWith("333")){ //simulando regra de negócio
            val statusProto: com.google.rpc.Status = com.google.rpc.Status.newBuilder()
                .setCode(Code.PERMISSION_DENIED.number)
                .setMessage("sem permissão para acessar esse recurso")
                .addDetails(Any.pack(
                    ErrorDetails.newBuilder().setCode(401).setMessage("token expirado").build()
                ))
                .build()

            responseObserver!!.onError(io.grpc.protobuf.StatusProto.toStatusRuntimeException(statusProto))
            return
        }

        val valor = Random.nextDouble(10.0, 100.0)
        val response = CepResponse.newBuilder().setCep(cep).setValor(valor).build()

        responseObserver!!.onNext(response)
        responseObserver.onCompleted()
    }
}