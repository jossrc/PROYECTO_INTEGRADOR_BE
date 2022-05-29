package com.postales.auth;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public abstract class SimpleGrantedAuthorityMixin {

    // Va el nombre de la propiedad (key) que contiene el rol (ubicado en el JWT)
    @JsonCreator
    public SimpleGrantedAuthorityMixin(@JsonProperty("role") String role) {}
}
