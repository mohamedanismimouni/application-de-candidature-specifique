package com.talan.polaris.dto;


import com.talan.polaris.entities.TestEntity;


public class DoneTestResultDto {
    private TestEntity test;

    public DoneTestResultDto() {
    }

    public DoneTestResultDto(TestEntity test) {
        this.test = test;
    }

    public TestEntity getTest() {
        return test;
    }

    public void setTest(TestEntity  test) {
        this.test = test;
    }

}
