package io.security.corespringsecurity.controller;

import lombok.Data;

@Data
public class DummyDto {
    String iam;

    public DummyDto() {
        this.iam ="dummy";
    }
}
