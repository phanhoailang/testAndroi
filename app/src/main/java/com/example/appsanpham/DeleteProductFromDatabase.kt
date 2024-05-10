package com.example.appsanpham

import android.util.Log
import com.google.firebase.database.FirebaseDatabase


fun deleteProductFromDatabase(productId: String, onComplete: () -> Unit) {
    val productRef = FirebaseDatabase.getInstance().getReference("products").child(productId)

    productRef.removeValue().addOnCompleteListener { task ->
        if (task.isSuccessful) {
            onComplete()
        } else {

            Log.e("Firebase", "Error deleting product", task.exception)
        }
    }
}