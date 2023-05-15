<script setup>

import {Lock, User} from "@element-plus/icons-vue";
</script>

<template>
    <div style="width:100vw;height: 100vh;overflow: hidden;display: flex">
        <div style="flex:1;background-color: dodgerblue">
            <el-image style="width: 100%;height: 100%" fit="cover"
                      src="https://s1.ax1x.com/2023/05/12/p9yH3Af.jpg"/>
        </div>
        <div class="welcome-title">
            <div style="font-size: 40px;font-width: bold;">LibraryPro</div>
            <div style="margin-top: 10px">-于遗世中清醒，于混沌中奋斗-</div>
        </div>
        <div style="width: 500px;background-color: white;z-index:1">
            <router-view v-slot="{Component}">
                <transition name="el-fade-in-linear" mode="out-in">
                    <component :is="Component" style="height:100%"/>
                </transition>
            </router-view>
        </div>
    </div>
</template>

<style scoped>
.welcome-title{
    position: absolute;
    bottom: 30px;
    left: 30px;
    color:white;
    text-shadow: 0 0 10px black;

}
</style>