// backend/js/google-translate.js
async function translatePage(targetLang = 'en') {
    const apiKey = 'AIzaSyCGX5ORcbl9Ts-X-KHOUWx-66QTdlUEi-o'; // 👉 替换为你的 Google 翻译 API 密钥
    const elements = document.body.querySelectorAll('*');

    for (let el of elements) {
        // 跳过 script/style/meta 标签
        if (['SCRIPT', 'STYLE', 'META', 'LINK'].includes(el.tagName)) continue;
        if (el.childNodes.length === 1 && el.childNodes[0].nodeType === 3) {
            const text = el.textContent.trim();
            if (text && /[\u4e00-\u9fa5]/.test(text)) {  // 判断是否含中文
                try {
                    const res = await fetch(
                        `https://translation.googleapis.com/language/translate/v2?key=${apiKey}`,
                        {
                            method: 'POST',
                            body: JSON.stringify({
                                q: text,
                                target: targetLang,
                                format: 'text'
                            }),
                            headers: { 'Content-Type': 'application/json' }
                        }
                    );
                    const data = await res.json();
                    el.textContent = data.data.translations[0].translatedText;
                } catch (err) {
                    console.error('翻译失败:', text, err);
                }
            }
        }
    }
}
