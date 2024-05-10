package com.example.appsanpham

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.rememberImagePainter

@Composable
fun ProductItem(
    item: DataProduct,
    onEditClick: (String) -> Unit,
    onDeleteClick: (String) -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp)
            .border(width = 1.dp, color = Color(0xFF148DEE)),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = rememberImagePainter(item.productimage),
            contentDescription = item.productname,
            modifier = Modifier
                .size(80.dp)
                .padding(end = 10.dp)
                .padding(start = 10.dp)
        )

        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = buildAnnotatedString {
                    withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                        append("Tên: ")
                    }
                    withStyle(style = SpanStyle(fontWeight = FontWeight.Normal)) {
                        append(item.productname)
                    }
                },
                fontSize = 18.sp
            )
            Text(
                text = buildAnnotatedString {
                    withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                        append("Giá: ")
                    }
                    withStyle(style = SpanStyle(fontWeight = FontWeight.Normal)) {
                        append(item.productprice)
                    }
                },
                fontSize = 18.sp
            )
            Text(
                text = buildAnnotatedString {
                    withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                        append("Loại: ")
                    }
                    withStyle(style = SpanStyle(fontWeight = FontWeight.Normal)) {
                        append(item.producttype)
                    }
                },
                fontSize = 18.sp
            )
        }

        Column(
            modifier = Modifier
                .padding(horizontal = 8.dp) ,
            horizontalAlignment = Alignment.End
        ) {
            IconButtonWithBorder(
                icon = Icons.Filled.Edit,
                onClick = {  onEditClick(item.productId)  },
                borderColor = Color.Yellow,
                iconColor = Color.Yellow
            )
            Spacer(modifier = Modifier.height(5.dp))
            IconButtonWithBorder(
                icon = Icons.Filled.Delete,
                onClick = { onDeleteClick(item.productId)  },
                borderColor = Color.Red,
                iconColor = Color.Yellow
            )



        }
    }
}

@Composable
fun IconButtonWithBorder(
    icon: ImageVector,
    onClick: () -> Unit,
    borderColor: Color,
    iconColor: Color
) {
    IconButton(
        onClick = onClick,
        modifier = Modifier
            .border(1.dp, borderColor)
            .size(32.dp)
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = iconColor
        )
    }
}
