package com.example

import com.amazonaws.serverless.proxy.internal.testutils.AwsProxyRequestBuilder
import com.amazonaws.serverless.proxy.internal.testutils.MockLambdaContext
import com.amazonaws.serverless.proxy.model.AwsProxyRequest
import com.amazonaws.serverless.proxy.model.AwsProxyResponse
import com.amazonaws.services.lambda.runtime.Context
import io.micronaut.function.aws.proxy.MicronautLambdaHandler
import io.micronaut.http.HttpMethod
import io.micronaut.http.HttpStatus
import spock.lang.AutoCleanup
import spock.lang.Shared
import spock.lang.Specification
import com.amazonaws.serverless.proxy.model.Headers

import java.nio.charset.StandardCharsets

class UserControllerSpec extends Specification {

    @Shared
    @AutoCleanup
    MicronautLambdaHandler handler = new MicronautLambdaHandler()

    @Shared
    Context lambdaContext = new MockLambdaContext()

    void 'show should find the user'() {
        given:
        String email = 'email+alias@gmail.com'
        String encoded = URLEncoder.encode(email, StandardCharsets.UTF_8.toString())
        String encodedTwice = URLEncoder.encode(encoded, StandardCharsets.UTF_8.toString())
        String path = "/users/${encodedTwice}"

        when:
        AwsProxyRequest request = new AwsProxyRequestBuilder(path, HttpMethod.GET.toString()).build()
        Headers headers = new Headers();
        headers.add("qparam", "foo+bar");
        request.setMultiValueQueryStringParameters(headers)
        AwsProxyResponse response = handler.handleRequest(request, lambdaContext)

        then:
        response.statusCode == HttpStatus.OK.code
    }
}
