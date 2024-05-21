package com.example.demo;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "lab4_json", value = "/rep_json2")
public class lab4_json extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static final String FILE_PATH = "C:\\Users\\4\\IdeaProjects\\demo\\src\\main\\java\\com\\example\\demo\\rep.json"; // Путь к файлу на сервере

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        Gson gson = new Gson();
        List<Rep> reps = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            Type listType = new TypeToken<ArrayList<Rep>>() {
            }.getType();
            reps = gson.fromJson(reader, listType);
            response.getWriter().write(gson.toJson(reps));
            // конвертируем и записываем на страничку json объекты
        } catch (IOException e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Ошибка");
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        StringBuilder jsonRequest = new StringBuilder();
        String line;
        try (BufferedReader reader = request.getReader()) {
            while ((line = reader.readLine()) != null) {
                jsonRequest.append(line);
            } // читаем пост запрос в переменную
        } catch (IOException e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Ошибка");
            return;
        }

        Gson gson = new GsonBuilder().create();
        Rep rep = gson.fromJson(jsonRequest.toString(), Rep.class);

        // Чтение текущего списка из файла
        List<Rep> reps = new ArrayList<>();
        try (BufferedReader fileReader = new BufferedReader(new FileReader(FILE_PATH))) {
            Type listType = new TypeToken<ArrayList<Rep>>() {
            }.getType();
            reps = gson.fromJson(fileReader, listType);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Добавление записи в список
        reps.add(rep);

        // Запись списка обратно в файл
        try (FileWriter fileWriter = new FileWriter(FILE_PATH)) {
            gson.toJson(reps, fileWriter);
//            fileWriter.write(gson.toJson(reps));
        } catch (IOException e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Ошибка");
            return;
        }

        // Отправка обновленного списка
        doGet(request, response);
    }
}