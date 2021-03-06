package com.baidu.unbiz.fluentvalidator;

import static com.baidu.unbiz.fluentvalidator.ResultCollectors.toSimple;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

import java.util.List;

import org.junit.Test;

import com.baidu.unbiz.fluentvalidator.dto.Car;
import com.baidu.unbiz.fluentvalidator.validator.CarValidator;
import com.baidu.unbiz.fluentvalidator.validator.CarValidator2;
import com.baidu.unbiz.fluentvalidator.validator.CarValidator3;
import com.google.common.collect.Lists;

/**
 * @author zhangxu
 */
public class FluentValidatorOnEachTest {

    @Test
    public void testCarCollection() {
        List<Car> cars = getValidCars();

        Result ret = FluentValidator.checkAll()
                .onEach(cars, new CarValidator())
                .onEach(cars, new CarValidator2())
                .onEach(cars, new CarValidator3())
                .doValidate()
                .result(toSimple());
        System.out.println(ret);
        assertThat(ret.isSuccess(), is(true));
    }

    @Test
    public void testCarCollectionNegative() {
        List<Car> cars = getValidCars();
        cars.get(0).setSeatCount(0);
        cars.get(1).setLicensePlate("hehe");

        Result ret = FluentValidator.checkAll().failOver()
                .onEach(cars, new CarValidator())
                .onEach(cars, new CarValidator2())
                .onEach(cars, new CarValidator3())
                .doValidate()
                .result(toSimple());
        System.out.println(ret);
        assertThat(ret.isSuccess(), is(false));
        assertThat(ret.getErrorNumber(), is(2));
    }

    @Test
    public void testCarArray() {
        Car[] cars = getValidCars().toArray(new Car[] {});

        Result ret = FluentValidator.checkAll()
                .onEach(cars, new CarValidator())
                .onEach(cars, new CarValidator2())
                .onEach(cars, new CarValidator3())
                .doValidate()
                .result(toSimple());
        System.out.println(ret);
        assertThat(ret.isSuccess(), is(true));
    }

    @Test
    public void testCarArrayNegative() {
        Car[] cars = getValidCars().toArray(new Car[] {});
        cars[0].setSeatCount(0);
        cars[1].setLicensePlate("hehe");

        Result ret = FluentValidator.checkAll().failOver()
                .onEach(cars, new CarValidator())
                .onEach(cars, new CarValidator2())
                .onEach(cars, new CarValidator3())
                .doValidate()
                .result(toSimple());
        System.out.println(ret);
        assertThat(ret.isSuccess(), is(false));
        assertThat(ret.getErrorNumber(), is(2));
    }

    @Test
    public void testNull() {
        List<Car> cars = null;

        Result ret = FluentValidator.checkAll()
                .onEach(cars, new CarValidator())
                .onEach(cars, new CarValidator2())
                .onEach(cars, new CarValidator3())
                .doValidate()
                .result(toSimple());
        System.out.println(ret);
        assertThat(ret.isSuccess(), is(true));
    }

    private List<Car> getValidCars() {
        return Lists.newArrayList(new Car("BMW", "LA1234", 5),
                new Car("Benz", "NYCuuu", 2),
                new Car("Chevrolet", "LA1234", 7));
    }

}
