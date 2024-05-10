package com.example.appsanpham


import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

fun addProductToDatabase(productName: String, producttype: String, productPrice: String, productImageUrl: String, callback: () -> Unit) {
    val database = Firebase.database
    val myRef = database.getReference("products")

    val productId = myRef.push().key

    val product =
        productId?.let { DataProduct(it, productName, producttype, productPrice , productImageUrl) }


    if (productId != null && product != null ) {
        myRef.child(productId).setValue(product)
            .addOnSuccessListener {
                callback()
            }
            .addOnFailureListener {

            }
    }
}
