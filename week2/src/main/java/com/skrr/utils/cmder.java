package com.skrr.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class cmder {

    public static void main(String[] args) throws IOException, InterruptedException {
        if (args != null && args.length > 1) {
            String action = args[0];
            String content = args[1];
            if (content.startsWith("\\u")) {
                content = unicodeToOrign(content);
            }
            String fullCmd = action + " " + content;
            System.out.println(fullCmd);
            run_command(partitionCommandLine(fullCmd), new File("/Users/wuliang/Documents/geek/code/temp"));
        }
    }

    public static String unicodeToOrign(String str) {
        Pattern pattern = Pattern.compile("(\\\\u(\\p{XDigit}{4}))");
        Matcher matcher = pattern.matcher(str);
        char ch;
        while (matcher.find()) {
            ch = (char) Integer.parseInt(matcher.group(2), 16);
            str = str.replace(matcher.group(1), ch + "");
        }
        return str;
    }

    public static String[] partitionCommandLine(final String command) {
        final ArrayList<String> commands = new ArrayList<>();
        int index = 0;
        StringBuffer buffer = new StringBuffer(command.length());

        boolean isApos = false;
        boolean isQuote = false;
        while (index < command.length()) {
            final char c = command.charAt(index);
            switch (c) {
                case ' ':
                    if (!isQuote && !isApos) {
                        final String arg = buffer.toString();
                        buffer = new StringBuffer(command.length() - index);
                        if (arg.length() > 0) {
                            commands.add(arg);
                        }
                    } else {
                        buffer.append(c);
                    }
                    break;
                case '\'':
                    if (!isQuote) {
                        isApos = !isApos;
                    } else {
                        buffer.append(c);
                    }
                    break;
                case '"':
                    if (!isApos) {
                        isQuote = !isQuote;
                    } else {
                        buffer.append(c);
                    }
                    break;
                default:
                    buffer.append(c);
            }
            index++;
        }

        if (buffer.length() > 0) {
            final String arg = buffer.toString();
            commands.add(arg);
        }
        return commands.toArray(new String[commands.size()]);
    }

    public static boolean run_command(final String[] command, final File work_path) throws IOException, InterruptedException {
//        System.out.println("COMMAND:" + command);
        List<String> result_list = new ArrayList<>();
        ProcessBuilder hiveProcessBuilder = new ProcessBuilder(command);
        hiveProcessBuilder.directory(work_path);
        hiveProcessBuilder.redirectErrorStream(true);
        Process hiveProcess = hiveProcessBuilder.start();
        BufferedReader std_input = new BufferedReader(new InputStreamReader(hiveProcess.getInputStream(), "UTF-8"));
        BufferedReader std_error = new BufferedReader(new InputStreamReader(hiveProcess.getErrorStream(), "UTF-8"));
        String line;
        while ((line = std_input.readLine()) != null) {
            result_list.add(line);
        }
        while ((line = std_error.readLine()) != null) {
            System.out.println(line);
        }
        hiveProcess.waitFor();
        if (hiveProcess.exitValue() != 0) {
            System.out.println("failed to execute:" + command);
            return false;
        }
        System.out.println("execute success:" + command);
        std_input.close();
        std_error.close();
        return true;
    }
}
