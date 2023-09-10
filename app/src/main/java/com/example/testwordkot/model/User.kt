package com.example.testwordkot.model


data class User(
    private var name: String,
    private var email: String,
    private var password: String,
    private var phone: String,
    private var classE: String
) {


    fun toMap(): Map<String, Any> {
        return hashMapOf(
            "name" to name,
            "email" to email,
            "password" to password,
            "phone" to phone,
            "class" to classE
        )
    }

    fun getName(): String = name

    fun setName(name: String) {
        this.name = name
    }

    fun getEmail(): String = email

    fun setEmail(email: String) {
        this.email = email
    }

    fun getPassword(): String = password

    fun setPassword(password: String) {
        this.password = password
    }

    fun getPhone(): String = phone

    fun setPhone(phone: String) {
        this.phone = phone
    }

    fun getClassE(): String = classE

    fun setClassE(classE: String) {
        this.classE = classE
    }

}