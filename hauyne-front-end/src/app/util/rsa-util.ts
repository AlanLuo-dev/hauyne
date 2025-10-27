/**
 * Base64 字符串 → ArrayBuffer
 */
export function base64ToArrayBuffer(b64: string): ArrayBuffer {
    const binary = atob(b64);
    const buffer = new ArrayBuffer(binary.length);
    const view = new Uint8Array(buffer);
    for (let i = 0; i < binary.length; i++) {
        view[i] = binary.charCodeAt(i);
    }
    return buffer;
}

/**
 * 从 Base64 导入 RSA 公钥（SPKI 格式）
 */
export function importRsaPublicKeyBase64(b64Key: string): Promise<CryptoKey> {
    const keyData = base64ToArrayBuffer(b64Key);
    return crypto.subtle.importKey(
        "spki", // X.509 SubjectPublicKeyInfo 格式
        keyData,
        {
            name: "RSA-OAEP",
            hash: "SHA-256",
        },
        false,
        ["encrypt"]
    );
}

/**
 * 私钥base64转换为CryptoKey对象
 * @param b64Key 私钥base64字符串
 * @returns 私钥CryptoKey对象
 */
export function importRsaPrivateKeyBase64(b64Key: string): Promise<CryptoKey> {
    const keyData = base64ToArrayBuffer(b64Key);
    return crypto.subtle.importKey(
        "pkcs8",                        // 私钥必须用 "pkcs8"
        keyData,
        {name: "RSA-OAEP", hash: "SHA-256"}, // 算法和哈希方式
        false,                          // 不可导出
        ["decrypt"]                     // 用于解密
    );
}


/**
 * 公钥加密
 * @param publicKey 公钥CryptoKey对象
 * @param data 待加密数据
 */
export async function encryptAsymmetricKey(publicKey: CryptoKey, data: any) {
    const encodedData = new TextEncoder().encode(data);
    const encryptedData: ArrayBuffer = await window.crypto.subtle.encrypt(
        {
            name: "RSA-OAEP",
        },
        publicKey,
        encodedData
    );

    // 加密结果转 Base64
    return btoa(String.fromCharCode(...new Uint8Array(encryptedData)));
}

/**
 * 私钥解密
 * @param privateKey 私钥CryptoKey对象
 * @param encryptedData 待解密数据
 */
export async function decryptAsymmetricKey(privateKey: CryptoKey, encryptedData: any) {
    const encryptedArrayBuffer = base64ToArrayBuffer(encryptedData); // 转 ArrayBuffer
    const decryptedData = await window.crypto.subtle.decrypt(
        {
            name: "RSA-OAEP",
        },
        privateKey,
        encryptedArrayBuffer
    );
    return new TextDecoder().decode(decryptedData);
}
