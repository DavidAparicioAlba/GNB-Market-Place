package com.example.gnbmarketplace.core

sealed class Failure {
    object NetworkConnection : Failure()
    class ServerErrorCode(val code: Int): Failure()
    class ServerException(val throwable: Throwable): Failure()

    abstract class FeatureFailure: Failure()

    class NullResult: FeatureFailure()
}