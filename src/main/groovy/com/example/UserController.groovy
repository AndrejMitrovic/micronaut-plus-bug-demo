package com.example

import groovy.transform.CompileStatic
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.PathVariable
import io.micronaut.http.annotation.QueryValue

@Controller('/users')
@CompileStatic
class UserController {

    Set<User> users = [
        new User(email: 'email+alias@gmail.com')
    ].toSet()

    @Get('/{+email}')
    User show(@PathVariable String email, @QueryValue String qparam) {
        println "Query parameter is: ${qparam}"
        users.find { it.email == email }
    }
}
