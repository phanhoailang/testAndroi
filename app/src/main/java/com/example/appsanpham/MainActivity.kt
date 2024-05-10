package com.example.appsanpham

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.appsanpham.ui.theme.AppsanphamTheme
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.TextButton


class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AppsanphamTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    ProductInput()
                }
            }
        }
    }
}




@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductInput() {
    var productName by remember { mutableStateOf("") }
    var productPrice by remember { mutableStateOf("") }
    var producttype by remember { mutableStateOf("") }
    var productImageUrl by remember { mutableStateOf("") }


    var productList by remember { mutableStateOf<List<DataProduct>?>(null) }

    LaunchedEffect(Unit) {
        getAllProductsFromDatabase { products ->
            productList = products
        }
    }

    Column(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            text = "Dữ liệu sản phẩm",
            fontSize = 23.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 10.dp)
        )
        OutlinedTextField(
            value = productName,
            onValueChange = { productName = it },
            label = { Text("Tên sản phẩm") },
            modifier = Modifier.fillMaxWidth(),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                unfocusedBorderColor =  Color(0xFF148DEE),
            )

        )
        Spacer(modifier = Modifier.height(10.dp))

        OutlinedTextField(
            value = producttype,
            onValueChange = { producttype = it },
            label = { Text("Loại sản phẩm") },
            modifier = Modifier.fillMaxWidth(),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                unfocusedBorderColor =  Color(0xFF148DEE),
            )
        )

        Spacer(modifier = Modifier.height(10.dp))

        OutlinedTextField(
            value = productPrice,
            onValueChange = { productPrice = it },
            label = { Text("Giá sản phẩm") },
            modifier = Modifier.fillMaxWidth(),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                unfocusedBorderColor =  Color(0xFF148DEE),
            )
        )

        Spacer(modifier = Modifier.height(10.dp))



        OutlinedTextField(
            value = productImageUrl,
            onValueChange = { productImageUrl = it },
            label = { Text("Ảnh") },
            modifier = Modifier
                .fillMaxWidth(),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                unfocusedBorderColor = Color(0xFF148DEE),
            )
        )


        Spacer(modifier = Modifier.height(10.dp))

        val mainButtonColor = ButtonDefaults.buttonColors(
            containerColor = Color(0xFFE91E63),
            contentColor = MaterialTheme.colorScheme.surface
        )


        Button(
            colors = mainButtonColor,
            onClick = {
                addProductToDatabase(
                    productName,
                    producttype,
                    productPrice,
                    productImageUrl,
                ){
                    getAllProductsFromDatabase { products ->
                        productList = products
                    }
                }
                productName = ""
                producttype = ""
                productPrice = ""
                productImageUrl = ""


            },
            modifier = Modifier.align(Alignment.CenterHorizontally)
        ) {
            Text(text = "Thêm sản phẩm")
        }
        Spacer(modifier = Modifier.height(10.dp))

        Text(
            text = "Tất cả sản phẩm",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 10.dp)
        )
        val openAlertDialog = remember { mutableStateOf(false) }

        val productIdState = remember{ mutableStateOf("")}
        val productNameState = remember { mutableStateOf("") }
        val productTypeState = remember { mutableStateOf("") }
        val productPriceState = remember { mutableStateOf("") }
        val productImageUrlState = remember { mutableStateOf("") }

          /// hop thoai xoa san pham
        var showDialog by remember { mutableStateOf(false) }
        var productIdToDelete by remember { mutableStateOf("") }

        if (showDialog) {
            AlertDialog(
                onDismissRequest = {
                    showDialog = false
                    productIdToDelete = ""
                },
                title = {
                    Text("confirm deletion")
                },
                text = {
                    Text("Are you sure delete?")
                },
                confirmButton = {
                    TextButton(
                        onClick = {
                            deleteProductFromDatabase(productIdToDelete) {
                                showDialog = false
                                productIdToDelete = ""
                                getAllProductsFromDatabase { products ->
                                    productList = products
                                }
                            }
                        }
                    ) {
                        Text("Xóa")
                    }
                },
                dismissButton = {
                    TextButton(
                        onClick = {
                            showDialog = false
                            productIdToDelete = ""
                        }
                    ) {
                        Text("Hủy")
                    }
                }
            )
        }

        LazyColumn(content = {
            productList?.let { products ->
                items(products) { product ->
                    ProductItem(
                        item = product,
                        onEditClick = { productId ->
                            openAlertDialog.value = true
                            getProductFromDatabase(productId) { selectedProduct ->
                                productIdState.value = selectedProduct!!.productId
                                productNameState.value = selectedProduct.productname
                                productTypeState.value = selectedProduct.producttype
                                productPriceState.value = selectedProduct.productprice
                                productImageUrlState.value = selectedProduct.productimage

                                Log.d("SelectedProduct", selectedProduct.toString())
                            }
                        },
                        onDeleteClick = { productId ->
                            productIdToDelete = productId
                            showDialog = true
                        }
                    )
                }
            }
        })



        if (openAlertDialog.value) {

            ProductItemDialog(
                onDismissRequest = { openAlertDialog.value = false },
                onConfirmation = {
                    updateProductInDatabase(
                        productIdState.value,
                        productNameState.value,
                        productTypeState.value,
                        productPriceState.value,
                        productImageUrlState.value
                    ) {
                        getAllProductsFromDatabase { products ->
                            productList = products
                        }
                    }
                    openAlertDialog.value = false
                },
                dialogTitle = "Repair products",
                productName = productNameState.value,
                productType = productTypeState.value,
                productPrice = productPriceState.value,
                productImageUrl = productImageUrlState.value,
                onProductNameChange = { productNameState.value = it },
                onProductTypeChange = { productTypeState.value = it },
                onProductPriceChange = { productPriceState.value = it },
                onProductImageUrlChange = { productImageUrlState.value = it }
            )
        }


    }
}







@Preview(showBackground = true)

@Composable
fun ProductInputPreview() {

    AppsanphamTheme {
        ProductInput()
    }
}







