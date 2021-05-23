package com.test.controller.alipay;

/**
 * 扫码支付配置参数
 */
public class AlipayConfig {
    //发起请求的应用ID。沙箱与线上不同，请更换代码中配置;
    public static String app_id ="2016092100564718";
    //支付宝应用RSA私匙
    public static String merchant_private_key = "MIIEvAIBADANBgkqhkiG9w0BAQEFAASCBKYwggSiAgEAAoIBAQCuG2vGc2d3h2cxYOYfFckOcjCvKtdIz7VzgrGpik93XN4OuLm4W42GzFortJbjJslvWPc/VNUaO9+wxyLZtqYkLlAqy+AgV1u+fd4HBzpEGJUDyPUfqXs/eegf/BslJc9J2/8SjM3t0h4X/YFmDsyzXYxjQhuQ6yNG/Q76EdT+Peh8TMZd8/pnMbrWnoR5oezbfb0/99YdWh6Gtj7hUOsMKcIoYCAAB1+Rt8zN9K+ASfyyQnCODmEQrfqwak0M7zJ06LrUkk5eztsSSqix4CF/JqBehCTqzx7eH15ypxcuP4a2wz83wrlUofsYgc+eTPUHrDPz4fJvG6tIrWCsX1NNAgMBAAECggEAW6f529ZDIOy6SYdufezVFHLO6FhQT3dxWaby6oLyhK7yA6QBuWkRH2aTDbEbQefYYXLfboq1SQM+Jm0fDqgV3LQ45d7AVaZHmQfgC6klZlXelMiFKq7pQChoJ/ZrP+ogrwxOwrA3dLyJtS1yooyl/MMBtftGUctHngZ4jPM4QsLo6vDO2NqLr6DrGVwys/bfWgJCR1o2psbT/KDEpsqT0c+W6tL6niHdxYbYkoDt00PDLw7XVXF55H5OGSQHoGWKS1lt9cehJ25T1QfbJmn+EyH4RduOWpWbhpm3BpI40x+RjZNfCSnN618Fh+WbR7xbJ8OrV9Doul7lTx7MJGcBYQKBgQD7NTuUH9TxBMOzUJyS1EMjGKkVTvoui8BYM8rmgttcF8LzsFZ+X+HkNCQGxgNRUq8P5jBIAiLKmQs/35IGde/z6b0UABjPKgwsPAIwUrP66vV0XcVgGi58C3sJtYGRpO5jtNt3WTOy/pCyrboUH2QQO979Jlw7k527YGpPzwj11QKBgQCxbaser5QkyRjN7ye/rZCvj8iRC8Bbd/IoLxMjEQG740XkoSzfmOLQMFy1XOnwHV+EPBtAoy8BCsY3MBK3zVrziPBqOKQnyEaBjLchwVbIZwDk1LGHoqkQfW93O49/5uktLeZKzM+hd2Yf7wyn+ni13uMpb4fF5VOKjIbcdINLmQKBgDep8gu+ifbmMV84i5lVg5LxUfYwy+soeHSS9XDiqizb7w0ElAXoxStGCn2dvvp0Mep5/VTXdFIkW4PJzJe15nfL4WPfKaAuFwhuO6XtjYgaLE2ewa+r3YULHjZmi9F0FIbvenoVwAj3s6SELmlrE0ctnRnHOETTTu5ITQZmP/wJAoGAR5pKhIe28WF2cSUilWgmC7Oq7V4gssklILJetrgWSwzdF/czdMjuP7ZEs6xbfFteQK9BEETKo1EFzR/JbgOhwMZyv2s8VBGu4nk3LpgnvZhT9AUh3+r074KviR6wOp2lXGY5mdYttKvKuvht0xxxunPImOA5mtOBc1hi1HTwx4ECgYBbdxs6Kuyg5kV09kS7wC23jaqF/Y8gwBTphfu+IAcf3PA/+Ri8f+gGBl8dC1OUVl4U3TbBOXIbgqMNjLO1A2Q46Dx2Ox0u7hTMst5a7AlhWX//72DHcZJHf8p/mAh22+XApfqpYc4KDkQYLPw7NJWpomWAZkNX1ajQJSI3xyYQIA==";
    //支付宝公匙
    public static String alipay_public_key = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAs2Tmb7FYQP1yWKIzoB9Qpu87wqrFhJDEjcIolOKcjL0ZraA/NJ6hi3L38COMjV9IN9e3dvbRQTcPdAvTWm/Jh7oplLgsZs4/BNBLU9W2o33Hjjk7PO9XwemTyI75hMZoSFIn4SJ1adNHRtoOkPteVraFxa+5JQ0OnOlRPqZSocPM/ytEIaKftyLIHHI9y4npo0Uzqi8KiqQ0Wzvr03YDyBCB2pOrYNig8S4jKra+GzfEEA65M0yyOznvHp9H4brd0uaH4m9RXlgD2e540hTfmsDu7UghdkBThQwcVlSOwAOOxBPC63Ks9foabf0LaflABZyFuEeV0i6VI5e52f1eEQIDAQAB";
    //支付宝应用RSA公匙
    //public static String merchant_public_key = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAjKi3GPSwBII9yNbUnDVlAk8DRSPzct/JrV5fQOZy3rLSnyUI8vWLLaapa3eTWFdr2WuxIyZxKuJKdCm7Gv038Lcx8w1c1wRXkaiX29htWaVgToFO2YcmAqQKUBCn9o2J3c/5YTrw6eVB4sEdmv3B5xjyKv3MY/Q1AJ0sLJd+mwCaYHWaAXpnJoUT0nUPYdV5Lf9Qhl37iNpsjY5UayzWbdB+pQFE2QXKy3cZ1CLyriaaFlLTfTFe2e3guWJn6JqO4BYtEy4G/7gmoq1TmL553ZWvZjCOWrRUfg1aZLIFDWHygrVPceZZKUjnwBhy20WSAM9sfbntFeAVeEr6b7cbfwIDAQAB";
    //服务器异步通知路径
    public static String notify_url = "http://cj.ngrok2.xiaomiqiu.cn/alipay/qrcode/asyncCallBack.action";
    //服务器同步通知路径
    //public static String return_url = "http://cj.ngrok2.xiaomiqiu.cn/alipay/qrcode/synchronizationCallBack.action";
    //公匙类型/签名类型
    public static String sign_type = "RSA2";
    //编码格式
    public static String charset = "utf-8";
    //向支付宝发起请求的网关。沙箱与线上不同，请更换代码中配置;
    // 沙箱:https://openapi.alipaydev.com/gateway.do
    // 上线:https://openapi.alipay.com/gateway.do
    public static String gatewayUrl = "https://openapi.alipaydev.com/gateway.do";
}