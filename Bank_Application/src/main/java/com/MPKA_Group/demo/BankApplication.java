package com.MPKA_Group.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;

@SpringBootApplication
@OpenAPIDefinition
(
    info = @Info
    (
        title="The MPKA Groups Of Bank",
        description="Banking application using REST API",
        version="v3.0",
        contact=@Contact
        (
            name="Nishanth",
            email="mpkagroupsodbank@gmail.com",
            url="https://github.com/nish333"
        ),
        license=@License
        (
            name="The MPKA Boys",
            url="https://github.com/dashboard"
        )
    ),
    externalDocs = @ExternalDocumentation
    (
        description="We make your payment secure and successfully.",
        url="https://mvnrepository.com/"
    )
)


public class BankApplication {

    public static void main(String[] args) {
        SpringApplication.run(BankApplication.class, args);
    }

}
