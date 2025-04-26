// backend/js/google_translate.js

const apiKey = 'AIzaSyCGX5ORcbl9Ts-X-KHOUWx-66QTdlUEi-o';

async function translateElementText(el, targetLang = 'en') {
    const text = el.textContent.trim();
    if (!text || !/[\u4e00-\u9fa5]/.test(text)) return;

    try {
        const res = await fetch(
            `https://translation.googleapis.com/language/translate/v2?key=${apiKey}`,
            {
                method: 'POST',
                body: JSON.stringify({ q: text, target: targetLang, format: 'text' }),
                headers: { 'Content-Type': 'application/json' }
            }
        );
        const data = await res.json();
        el.textContent = data.data.translations[0].translatedText;
    } catch (err) {
        console.error('翻译失败:', text, err);
    }
}

async function translateDocument(doc, targetLang = 'en') {
    const elements = doc.body.querySelectorAll('*');
    for (let el of elements) {
        if (['SCRIPT', 'STYLE', 'META', 'LINK'].includes(el.tagName)) continue;
        if (el.childNodes.length === 1 && el.childNodes[0].nodeType === 3) {
            await translateElementText(el, targetLang);
        }
    }
}

// 👇 主方法：从外部翻译 iframe 的页面内容
window.translateIframePage = async function (iframeId = 'cIframe', lang = 'en') {
    const iframe = document.getElementById(iframeId);
    if (!iframe) return console.warn('找不到 iframe 元素');

    try {
        const iframeDoc = iframe.contentDocument || iframe.contentWindow.document;
        if (!iframeDoc || !iframeDoc.body) return;

        await translateDocument(iframeDoc, lang);

        // 👇 可选：监听 DOM 变化
        const observer = new MutationObserver(
            debounce(() => {
                console.log('iframe DOM 变化，重新翻译...');
                translateDocument(iframeDoc, lang);
            }, 800)
        );
        observer.observe(iframeDoc.body, {
            childList: true,
            subtree: true
        });
    } catch (e) {
        console.error('翻译 iframe 页面失败:', e);
    }
};

function debounce(fn, delay = 500) {
    let timer = null;
    return function (...args) {
        clearTimeout(timer);
        timer = setTimeout(() => fn.apply(this, args), delay);
    };
}
