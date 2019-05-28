package com.freeefly;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class Person {

    private final String id;
    private final String firstName;
    private final String lastName;
    private final int age;

}
