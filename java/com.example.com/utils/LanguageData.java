package com.example.notes.utils;

import java.util.HashMap;

public class LanguageData {

    public static HashMap<String, HashMap<String, String>> data = new HashMap<>();

    public static void init() {

        // Format: key, en, id, ja, zh, ru, th
        String[][] translations = {

                // WELCOME
                {"welcome_title",
                        "Welcome to Notes",
                        "Selamat Datang Di Catatan",
                        "ノートへようこそ",
                        "欢迎使用笔记",
                        "Добро пожаловать в заметки",
                        "ยินดีต้อนรับสู่โน้ต"},

                {"welcome_next",
                        "Next",
                        "Berikutnya",
                        "次へ",
                        "下一步",
                        "Далее",
                        "ถัดไป"},

                // LOCATION
                {"location_title",
                        "Your Current Location",
                        "Lokasi anda saat ini",
                        "現在の位置情報",
                        "您目前的位置",
                        "Ваше текущее местоположение",
                        "ตำแหน่งปัจจุบันของคุณ"},

                {"location_detecting",
                        "Detecting location...",
                        "Mendeteksi lokasi...",
                        "位置情報を検出中…",
                        "正在检测位置…",
                        "Определение местоположения…",
                        "กำลังตรวจหาตำแหน่ง..."},

                {"location_next",
                        "Next",
                        "Berikutnya",
                        "次へ",
                        "下一步",
                        "Далее",
                        "ถัดไป"},

                // LANGUAGE
                {"language_title",
                        "Choose your language",
                        "Pilih bahasa anda",
                        "言語を選択してください",
                        "选择您的语言",
                        "Выберите язык",
                        "เลือกภาษาของคุณ"},

                {"language_button",
                        "Change language",
                        "Ganti bahasa",
                        "言語を変更",
                        "更改语言",
                        "Изменить язык",
                        "เปลี่ยนภาษา"},

                {"language_start",
                        "Start",
                        "Mulai",
                        "開始",
                        "开始",
                        "Начать",
                        "เริ่มต้น"},

                // HOME
                {"home_search_hint",
                        "Search notes…",
                        "Cari catatan…",
                        "メモを検索…",
                        "搜索笔记…",
                        "Поиск заметок…",
                        "ค้นหาบันทึก…"},


                {"home_recent_notes",
                        "Recent notes",
                        "Catatan terbaru",
                        "最近のメモ",
                        "最近的笔记",
                        "Недавние заметки",
                        "บันทึกล่าสุด"},

                {"home_empty",
                        "No notes yet",
                        "Belum ada catatan",
                        "メモがありません",
                        "还没有笔记",
                        "Пока нет заметок",
                        "ยังไม่มีบันทึก"},

                // CREATE NOTE
                {"note_title",
                        "Note title",
                        "Judul catatan",
                        "メモのタイトル",
                        "笔记标题",
                        "Название заметки",
                        "หัวข้อบันทึก"},

                {"note_title_hint",
                        "Write your title here…",
                        "Tulis judul kamu disini…",
                        "ここにタイトルを入力…",
                        "在这里填写标题…",
                        "Введите название…",
                        "เขียนหัวข้อที่นี่…"},


                {"note_content_hint",
                        "Write your note here…",
                        "Tulis catatan kamu disini…",
                        "ここにメモを入力…",
                        "在这里输入内容…",
                        "Введите текст заметки…",
                        "เขียนบันทึกของคุณที่นี่…"},


                {"note_save",
                        "Save note",
                        "Simpan catatan",
                        "保存",
                        "保存笔记",
                        "Сохранить",
                        "บันทึก"},

                // DELETE
                {"delete_title",
                        "Delete note?",
                        "Hapus catatan?",
                        "メモを削除しますか？",
                        "删除笔记？",
                        "Удалить заметку?",
                        "ลบบันทึก?"},

                {"delete_message",
                        "Are you sure you want to delete:",
                        "Yakin ingin menghapus:",
                        "削除しますか：",
                        "确定要删除：",
                        "Вы действительно хотите удалить:",
                        "คุณแน่ใจหรือไม่ว่าต้องการลบ:"},

                {"delete_cancel",
                        "Cancel",
                        "Batal",
                        "キャンセル",
                        "取消",
                        "Отмена",
                        "ยกเลิก"},

                {"delete_confirm",
                        "Delete",
                        "Hapus",
                        "削除",
                        "删除",
                        "Удалить",
                        "ลบ"},

                // CALENDAR
                {"calendar_title",
                        "Calendar",
                        "Kalender",
                        "カレンダー",
                        "日历",
                        "Календарь",
                        "ปฏิทิน"},

                // SEARCH
                {"search_title",
                        "Search",
                        "Cari",
                        "検索",
                        "搜索",
                        "Поиск",
                        "ค้นหา"}
        };

        // Bahasa yang dipakai
        String[] languages = {"en", "id", "ja", "zh", "ru", "th"};

        // Siapkan map
        for (String lang : languages) {
            data.put(lang, new HashMap<>());
        }

        // Isi data
        for (String[] row : translations) {
            String key = row[0];
            for (int i = 1; i < row.length; i++) {
                String lang = languages[i - 1];
                data.get(lang).put(key, row[i]);
            }
        }
    }

    public static String get(String lang, String key) {
        if (!data.containsKey(lang)) return data.get("en").get(key);
        return data.get(lang).getOrDefault(key, data.get("en").get(key));
    }
}
