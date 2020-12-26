package com.example.instabugtask.unit_test

import java.io.IOException
import com.example.instabugtask.Result

class TestRepo {
    fun getFullResponse(url: String): Result<String>{
        val html = "<!DOCTYPE html>\n" +
                "<html>\n" +
                "<head>\n" +
                "<title>Page Title</title>\n" +
                "</head>\n" +
                "<body>\n" +
                "\n" +
                "<h1>This is a Heading</h1>\n" +
                "<p>This is a paragraph.</p>\n" +
                "\n" +
                "</body>\n" +
                "</html>"

        if (url != "https://instabug.com") {
            return Result.Failure(html, IOException())

        }
        return Result.Success(html)
    }
}