<script setup>

import router from "@/router";
import {User,Lock,Message,EditPen} from "@element-plus/icons-vue";
import {reactive, ref} from "vue";
import {ElMessage} from "element-plus";
import {post} from "@/net";

const form = reactive({
    username:'',
    password:'',
    password_repeat:'',
    email:'',
    code:''
})
const coldTime = ref(0)
const validateEmail = ()=>{
    coldTime.value = 60;
    post('/api/auth/valid-register-email',{
        email: form.email
    },(message)=>{
        ElMessage.success(message);
        setInterval(()=>coldTime.value--,1000);
    },(message)=>{
        ElMessage.warning(message)
        coldTime.value = 0})
}
const formRef = ref()
const register = ()=>{
    formRef.value.validate((isValid)=>{
        if(isValid){
            post('/api/auth/register',{
                username:form.username,
                password:form.password,
                email:form.email,
                code:form.code
            },(message)=>{
                ElMessage.success(message)
                router.push("/")
            })
        }else{
            ElMessage.warning('请完整填写注册信息~')
        }
    })
}
const validateUsername = (rule,value,callback)=>{
    if(value==='')
        callback(new Error('请输入用户名~'))
    else if(!/^[a-zA-Z0-9\u4e00-\u9fa5]+$/.test(value)){
        callback(new Error('用户名非法,只能是中文/英文的组合~'))
    }else{
        callback()
    }
}
const validatePassword = (rule,value,callback) =>{
    if(value === ''){
        callback(new Error('请再次输入密码~'))
    }else if(value !== form.password){
        callback(new Error('两次输入的密码不一致~'))
    }else{
        callback()
    }
}
const rules = {
    username: [
        {
            validator:validateUsername,trigger:['blur','change']
        },
        {
            min:2,max:8,message: '用户名长度必须在2-8个字符之间~',trigger:['blur','change']
        }
    ],
    password:[
        {
          required:true,message: '请输入密码~',trigger: 'blur'
        },
        {
            min:6,max:16,message: '密码长度必须在6-16个字符之间~',trigger:['blur','change']
        }
    ],
    password_repeat:[
        {
            validator:validatePassword,trigger:['blur','change']
        }
    ],
    email:[
        {
            required:true,message: '请输入邮件地址~',trigger: 'blur'
        },
        {
            type:'email',message:'请输入合法的电子邮件地址~',trigger:['blur','change']
        }
    ],
    code:[
        {
            required:true,message: '请输入验证码~',trigger: 'blur'
        }
    ]
}
const isEmailValid = ref(false)

const onValidate = (prop,isValid)=>{
    if(prop === 'email')
        isEmailValid.value = isValid
}
</script>

<template>
    <div style="text-align: center;margin: 0 20px;padding: 20px">
        <div style="text-align: center;margin-top: 180px">
            <div style="font-size: 30px;font-weight: bold">
                注册新用户
            </div>
            <div style="font-size: 14px;color:grey;margin-top: 20px">
                欢迎注册LibraryPro,请在下方填写相关信息
            </div>
            <div style="margin-top: 50px">
                <el-form :model="form" :rules="rules" @validate="onValidate" ref="formRef">
                    <el-form-item prop="username">
                        <el-input v-model="form.username" :maxlength="8" type="text" placeholder="用户名">
                            <template #prefix>
                                <el-icon><User /></el-icon>
                            </template>
                        </el-input>
                    </el-form-item>
                    <el-form-item prop="password">
                        <el-input v-model="form.password" :maxlength="16" type="password" placeholder="密码" >
                            <template #prefix>
                                <el-icon><Lock /></el-icon>
                            </template>
                        </el-input>
                    </el-form-item>
                    <el-form-item prop="password_repeat">
                        <el-input v-model="form.password_repeat" :maxlength="16" type="password" placeholder="重复密码" >
                            <template #prefix>
                                <el-icon><Lock /></el-icon>
                            </template>
                        </el-input>
                    </el-form-item>
                    <el-form-item prop="email">
                        <el-input v-model="form.email" type="email" placeholder="电子邮件地址" >
                            <template #prefix>
                                <el-icon><Message /></el-icon>
                            </template>
                        </el-input>
                    </el-form-item>
                    <el-form-item prop="code">
                            <el-row style="width: 100%">
                                <el-col :span="18">
                                    <el-input v-model="form.code" :maxlength="6" type="text" placeholder="电子邮件验证码">
                                        <template #prefix>
                                            <el-icon><EditPen /></el-icon>
                                        </template>
                                    </el-input>
                                </el-col>
                                <el-col :span="4" style="margin-left: 3px">
                                    <el-button @click="validateEmail" type="success" plain  :disabled="!isEmailValid || coldTime > 0">
                                        {{coldTime>0 ? '请稍等 '+coldTime+'s':'获取验证码'}}</el-button>
                                </el-col>
                            </el-row>
                    </el-form-item>
                </el-form>
            </div>

        </div>
        <div style="margin-top: 60px">
            <el-button style="width: 300px" type="success" @click="register" plain>立即注册</el-button>
        </div>
        <el-divider>
            <span style="color: grey;font-size: 10px">已有账号</span>
        </el-divider>
        <div style="margin-top: 20px">
            <el-button @click="router.push('/')" style="width: 300px" type="warning" plain>立即登录</el-button>
        </div>
    </div>
</template>

<style scoped>

</style>