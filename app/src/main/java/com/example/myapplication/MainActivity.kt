package com.example.myapplication

import android.Manifest
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.documentfile.provider.DocumentFile
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberImagePainter
import com.anggrayudi.storage.file.*
import com.anggrayudi.storage.media.MediaStoreCompat
import com.anggrayudi.storage.permission.ActivityPermissionRequest
import com.anggrayudi.storage.permission.PermissionCallback
import com.anggrayudi.storage.permission.PermissionReport
import com.anggrayudi.storage.permission.PermissionResult
import com.hjq.permissions.OnPermissionCallback
import com.hjq.permissions.Permission
import com.hjq.permissions.XXPermissions
import java.io.File
import java.lang.Exception


private val TAG = "MainActivity1"

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            GetContentExample()
        }
    }
}

@Preview(name = "test")
@Composable
fun Screen() {
    val vm:MainViewModel= viewModel()
    Column {
        TextField(value = vm.count, onValueChange = {
            vm.count=it
        })
    }
    LaunchedEffect(key1 = vm.count){
        Log.d(TAG, "Screen: count:${vm.count}")
    }
}

@Composable
fun GetContentExample() {
    var imageUri by remember { mutableStateOf<Uri?>(null) }
    val c= LocalContext.current
    val launcher = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
        try {

            if (uri != null) {
                val f=DocumentFileCompat.fromUri(c, uri = uri)
                Log.d(TAG, "GetContentExample11: ${f?.getFormattedSize(c)}")
                f?.let {
                    val stream=it.openInputStream(c)
                    val bitmap=BitmapFactory.decodeStream(stream)
                    Log.d(TAG, "GetContentExample: height:${bitmap.height}\twidth:${bitmap.width}")
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        Log.d(TAG, "GetContentExample: ${uri.toString()}")
        imageUri = uri
    }
    Column {
        Button(onClick = { launcher.launch("image/*") }) {
            Text(text = "Load Image")
        }
        Button(onClick = {
            XXPermissions.with(c).
            permission(Permission.MANAGE_EXTERNAL_STORAGE).
            request(object : OnPermissionCallback {
                override fun onGranted(permissions: MutableList<String>, all: Boolean) {
                    if (all) {
                        Log.d(TAG, "onGranted: "+"获取录音和日历权限成功")
                    } else {
                        Log.d(TAG, "onGranted: "+"获取部分权限成功，但部分权限未正常授予")
                    }
                }

                override fun onDenied(permissions: MutableList<String>, never: Boolean) {
                    if (never) {
                        Log.d(TAG, "onGranted: "+"被永久拒绝授权，请手动授予录音和日历权限")
                        // 如果是被永久拒绝就跳转到应用权限系统设置页面
                        XXPermissions.startPermissionActivity(c, permissions)
                    } else {
                        Log.d(TAG, "onGranted: "+"获取录音和日历权限失败")
                    }
                }
            })
        }) {
            Text(text = "请求权限")
        }
        Image(
            painter = rememberImagePainter(imageUri),
            contentDescription = "My Image"
        )
    }
}