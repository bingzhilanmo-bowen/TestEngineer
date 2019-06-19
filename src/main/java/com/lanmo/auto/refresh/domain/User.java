package com.lanmo.auto.refresh.domain;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.lanmo.auto.refresh.serializer.PhoneSerializer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {

    private String name;

    private Integer age;

    @JsonSerialize(using = PhoneSerializer.class)
    private String phone;


}
