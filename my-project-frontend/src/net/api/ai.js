import {fetchPost} from "@/net";

export const apiChatWithAI = async (context, onMessage, onError, onComplete) => {
    try {
        const response = await fetchPost('/api/ai/chat', context)

        if (!response.ok || !response.body) {
            throw new Error(`AI 请求失败：HTTP ${response.status}`)
        }

        const reader = response.body.getReader();
        const decoder = new TextDecoder();
        let buffer = ''

        const consumeEvents = (flush = false) => {
            const parts = buffer.split(/\r?\n\r?\n/)
            if (!flush) buffer = parts.pop() || ''
            else buffer = ''
            for (const event of parts) {
                const data = event.split(/\r?\n/)
                    .filter(line => line.startsWith('data:'))
                    .map(line => line.slice(5).replace(/^ /, ''))
                    .join('\n')
                if (data) onMessage(data)
            }
        }

        while (true) {
            const { done, value } = await reader.read();
            if (done) break;
            buffer += decoder.decode(value, {stream: true})
            consumeEvents()
        }
        buffer += decoder.decode()
        if (buffer) {
            buffer += '\n\n'
            consumeEvents(true)
        }
        onComplete()
    } catch (e) {
        onError(e)
    }
}
