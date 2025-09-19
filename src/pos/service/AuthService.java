package pos.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AuthService {
    private String path = "resources/data/auth.txt";
    private Map<String, String> userpw = new HashMap<>();
    private Map<String, Integer> userToEmp = new HashMap<>();
    private Map<String, String> userToRole = new HashMap<>();

    public AuthService() {
        load();
    }

    public void load() {
        userpw.clear();
        userToEmp.clear();
        userToRole.clear();

        List<String> lines = FileUtil.readAllLines(path);
        for (String l : lines) {
            // username|password|employeeId|role
            String[] p = l.split("\\|", -1);
            try {
                String u = p[0];
                String pw = p[1];
                int emp = Integer.parseInt(p[2]);
                String role = p[3];

                userpw.put(u, pw);
                userToEmp.put(u, emp);
                userToRole.put(u, role);
            } catch (Exception ex) {
                // ignore malformed lines
            }
        }
    }

    public int login(String username, String password) {
        if (!userpw.containsKey(username)) return -1;

        String pw = userpw.get(username);
        if (pw.equals(password)) {
            return userToEmp.get(username);
        }
        return -1;
    }

    public String roleOf(String username) {
        return userToRole.get(username);
    }

    public String findUsernameByEmpId(int empId) {
        for (Map.Entry<String, Integer> e : userToEmp.entrySet()) {
            if (e.getValue() == empId) return e.getKey();
        }
        return null;
    }

    public void addAuth(String username, String password, int empId, String role) {
        String line = username + "|" + password + "|" + empId + "|" + role;
        FileUtil.appendLine(path, line);
        load();
    }
}
