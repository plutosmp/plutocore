package top.plutomc.plutocore.utils;

public class LinkParser {

    private LinkParser() {
    }

    public static String parseUrl(String text, String hover) {
        String urlLinkRegex = "[(http(s)?):\\/\\/(www\\.)?a-zA-Z0-9@:%._\\+~#=]{2,256}\\.[a-z]{2,6}\\b([-a-zA-Z0-9@:%_\\+.~#?&//=]*)";
        String replacement = "<hover:show_text:'" + hover + "'><click:open_url:'$0'><aqua><u>$0</u></aqua></click></hover>".replace("{SPECIAL|||!@#$%^&*()|||SPECIAL}", hover);
        return text.replaceAll(urlLinkRegex, replacement);
    }
}
