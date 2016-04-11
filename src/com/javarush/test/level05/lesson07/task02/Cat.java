package com.javarush.test.level05.lesson07.task02;

/* Создать класс Cat
Создать класс Cat (кот) с пятью инициализаторами:
- Имя,
- Имя, вес, возраст
- Имя, возраст (вес стандартный)
- вес, цвет, (имя, адрес и возраст неизвестны, это бездомный кот)
- вес, цвет, адрес ( чужой домашний кот)
Задача инициализатора – сделать объект валидным. Например, если вес неизвестен, то нужно указать какой-нибудь средний вес. Кот не может ничего не весить. То же касательно возраста. А вот имени может и не быть (null). То же касается адреса: null.
*/

public class Cat
{
    //Напишите тут ваш код
    public String name, color, address;
    public int age, weight;

    public void initialize(String name) {
        this.name = name;
        this.weight = 4;
        this.age = 5;
        this.color = "red";
    }

    public void initialize(String name, int weight, int age) {
        this.name = name;
        this.weight = weight;
        this.age = age;
        this.color = "red";
    }

    public void initialize(String name, int age) {
        this.name = name;
        this.weight = 4;
        this.age = age;
        this.color = "red";
    }

    public void initialize(int weight, String color) {
        this.weight = weight;
        this.age = 5;
        this.color = color;
    }

    public void initialize(int weight, String color, String address) {
        this.weight = weight;
        this.age = 5;
        this.color = color;
        this.address = address;
    }
}
