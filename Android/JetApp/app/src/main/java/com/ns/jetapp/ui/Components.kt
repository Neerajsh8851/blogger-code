package com.ns.jetapp.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun BackgroundImage(
    modifier: Modifier = Modifier,
    painter: Painter,
    content: @Composable BoxScope.() -> Unit
) {

    Box(modifier = modifier.fillMaxSize()) {
        Image(
            painter = painter,
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )

        content()
    }
}


@Composable
fun ActionButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    color: Color,
    text: String,
    textColor: Color,
    padding: PaddingValues? = null,
) {
    OutlinedButton(
        modifier = modifier,
        onClick = onClick,
        contentPadding = padding ?: ButtonDefaults.ContentPadding,
        colors = ButtonDefaults.outlinedButtonColors(
            backgroundColor = color,
            contentColor = textColor
        ),
        border = BorderStroke(1.dp, textColor),
        shape = RoundedCornerShape(4.dp)
    ) {
        Text(
            text = text,
            fontWeight = FontWeight.Bold,
            fontSize = 14.sp
        )
    }
}


@Composable
fun InputField(
    modifier: Modifier = Modifier,
    text: String,
    onTextChange: (String) -> Unit,
    label: String,
    textColor: Color,
    trailingIcon: (@Composable () -> Unit)? = null,
    placeholder: (@Composable () -> Unit)? = null,
    visualTransformation: VisualTransformation = VisualTransformation.None
) {
    OutlinedTextField(
        value = text,
        onValueChange = onTextChange,
        colors = TextFieldDefaults.outlinedTextFieldColors(
            textColor = textColor,
            cursorColor = textColor,
            unfocusedBorderColor = textColor,
            focusedBorderColor = textColor
        ),
        label = { Text(text = label) },
        maxLines = 1,
        textStyle = TextStyle(
            fontSize = 14.sp,
            fontWeight = FontWeight.Medium
        ),
        visualTransformation = visualTransformation,
        trailingIcon = trailingIcon,
        placeholder = placeholder,
        modifier = modifier
    )
}



