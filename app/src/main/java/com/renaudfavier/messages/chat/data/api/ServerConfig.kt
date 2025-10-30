package com.renaudfavier.messages.chat.data.api

/**
 * Server configuration for the Messages API.
 *
 * To use with ngrok or a different server:
 * 1. Update BASE_URL with your server URL
 * 2. For ngrok: Run `ngrok http 3000` and copy the https URL
 * 3. For local testing: Use "http://10.0.2.2:3000" (Android emulator)
 *                   or "http://localhost:3000" (physical device with port forwarding)
 */
object ServerConfig {
    /**
     * Base URL for the API server.
     *
     * Examples:
     * - Local emulator: "http://10.0.2.2:3000"
     * - ngrok: "https://9f98-62-194-145-77.ngrok.io"
     * - Physical device: "http://YOUR_COMPUTER_IP:3000"
     */
    const val BASE_URL = "http://10.0.2.2:3000"
//    const val BASE_URL = "http://localhost:3000"
}
