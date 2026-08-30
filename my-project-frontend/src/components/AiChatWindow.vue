<script setup>
import {ref} from "vue";
import {CloseBold} from "@element-plus/icons-vue";
import {apiChatWithAI} from "@/net/api/ai";
import MarkdownIt from "markdown-it";

const md = new MarkdownIt()
const chatLoading = ref(false)
const isOpen = ref(false)
const inputText = ref('')

const messages = ref([])


const renderMarkdown = (text) => {
    return md.render(text)
}

const sendMessage = () => {
    if(inputText.value.trim() !=='') {
        messages.value.push({
            type: 'user',
            text: inputText.value,
            timestamp: new Date()
        })

        inputText.value = ''
        chatLoading.value = true

        messages.value.push({
            type: 'assistant',
            text: ' ',
            timestamp: new Date()
        })

        const context = [ ...messages.value ]
        context.length -= 1

        apiChatWithAI(context, text => {
            messages.value[messages.value.length - 1].text += text
        }, () => {
            messages.value[messages.value.length - 1].text = '生成失败，请重试'
            chatLoading.value = false
        }, () => {
            chatLoading.value = false
        })

    }


}
</script>

<template>
    <div class="ai-chat-window">
        <button v-if="!isOpen" @click="isOpen = true" class="chat-button">
            <span>💬</span>
        </button>
        <div v-else class="chat-window">
            <div class="chat-window__header">
                <h3>校园AI助手</h3>
                <button @click="isOpen = false" class="close-btn">
                    <el-icon><CloseBold /></el-icon>
                </button>
            </div>
            <div class="chat-window__messages">
                <div v-for="(message, index) in messages" :key="index"
                     :class="[`message-${message.type}`]">
                    <div class="message-text" v-if="message.type === 'user'">
                        {{ message.text }}
                    </div>
                    <div class="message-text markdown-body" v-if="message.type === 'assistant'"
                         v-html="renderMarkdown(message.text)">
                    </div>
                </div>
            </div>
            <div class="chat-window__input">
                <input v-model="inputText" @keydown.enter="sendMessage" type="text" placeholder="请输入要询问的内容...">
                <button @click="sendMessage" class="send-btn">发送</button>
            </div>
        </div>
    </div>
</template>

<style lang="less" scoped>
.ai-chat-window {
    position: fixed;
    bottom: 20px;
    right: 20px;
    z-index: 999;

    .chat-button {
        width: 42px;
        height: 42px;
        border-radius: 50%;
        background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
        border: none;
        box-shadow: var(--el-box-shadow-light);
        color: white;
        cursor: pointer;
        font-size: 18px;
        transition: transform .3s ease;

        &:hover {
            transform: scale(1.1);
        }
    }

    .chat-window {
        width: 380px;
        height: 500px;
        border-radius: 12px;
        box-shadow: var(--el-box-shadow-light);
        background: white;
        display: flex;
        flex-direction: column;
        animation: slideUp .3s ease;
        overflow: hidden;

        .chat-window__header {
            color: white;
            display: flex;
            justify-content: space-between;
            align-items: center;
            padding: 12px 16px;
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);

            h3 {
                margin: 0;
                font-size: 16px;
            }

            .close-btn {
                background: none;
                border: none;
                color: white;
                font-size: 20px;
                cursor: pointer;
                padding: 0;
                width: 30px;
                height: 30px;
            }
        }

        .chat-window__messages {
            flex: 1;
            padding: 16px;
            overflow-y: auto;
            display: flex;
            flex-direction: column;
            gap: 12px;
            background: #f5f5f5;

            .dark & {
                color: #e4e4e4;
                background: #222;
            }

            .message-user, .message-assistant {
                width: fit-content;
                max-width: 80%;

                .message-text {
                    background: white;
                    font-size: 14px;
                    padding: 8px 14px;
                    border-radius: 18px;
                    word-wrap: break-word;
                    overflow: hidden;

                    .dark & { background: #111; }
                }
            }

            .message-user {
                align-self: flex-end;
            }

            .message-assistant {
                align-self: flex-start;
            }
        }

        .chat-window__input {
            padding: 16px;
            background: white;
            border-top: 1px solid #e0e0e0;
            display: flex;
            gap: 8px;

            .dark & {
                background: #1a1a1a;
                border-color: #3c3c3c;
            }

            .send-btn {
                border: none;
                color: white;
                padding: 10px 20px;
                background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
                border-radius: 20px;
                font-size: 14px;
                cursor: pointer;
                transition: opacity .3s ease;

                &:hover {
                    opacity: .9;
                }
            }

            input {
                flex: 1;
                border-radius: 20px;
                border: 1px solid #e0e0e0;
                padding: 10px 14px;
                font-size: 14px;
                outline: none;

                &:focus {
                    border-color: #667eea;
                }

                .dark & {
                    border-color: #5c5c5c;
                }
            }
        }
    }
}

@keyframes slideUp {
    from {
        opacity: 0;
        transform: translateY(20px);
    }
    to {
        opacity: 1;
        transform: translateY(0);
    }
}
</style>
<style lang="less">
.markdown-body {
    p:first-child { margin-top: 0; }
    p:last-child { margin-bottom: 0; }
}
</style>

