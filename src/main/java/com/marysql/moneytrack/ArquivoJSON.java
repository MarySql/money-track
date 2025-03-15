package com.marysql.moneytrack;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ArquivoJSON {
    private static final String FILE_NAME = "gastos.json";
    private static final ObjectMapper mapper = new ObjectMapper();

    public static void salvarGastos(List<Gasto> gastos) {
        try {
            mapper.writeValue(new File(FILE_NAME), gastos);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static List<Gasto> carregarGastos() {
        File file = new File(FILE_NAME);
        if (!file.exists()) return new ArrayList<>();

        try {
            return mapper.readValue(file, new TypeReference<List<Gasto>>() {});
        } catch (IOException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }
}
