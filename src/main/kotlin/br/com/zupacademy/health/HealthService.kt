package br.com.zupacademy.health

import grpc.health.v1.HealthGrpc
import grpc.health.v1.HealthOuterClass
import io.grpc.stub.StreamObserver
import javax.inject.Singleton

@Singleton
class HealthService : HealthGrpc.HealthImplBase() {

    override fun check(request: HealthOuterClass.HealthCheckRequest?,
                       responseObserver: StreamObserver<HealthOuterClass.HealthCheckResponse>?) {

        responseObserver?.onNext(HealthOuterClass.HealthCheckResponse
             .newBuilder()
             .setStatus(HealthOuterClass.HealthCheckResponse.ServingStatus.SERVING)
             .build()
        )
        responseObserver?.onCompleted()
    }

    override fun watch(request: HealthOuterClass.HealthCheckRequest?,
                        responseObserver: StreamObserver<HealthOuterClass.HealthCheckResponse>?) {

        responseObserver?.onNext(HealthOuterClass.HealthCheckResponse
            .newBuilder()
            .setStatus(HealthOuterClass.HealthCheckResponse.ServingStatus.SERVING)
            .build()
        )
    }
}