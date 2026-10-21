package mypackage;

import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

/**
 * SwingAwtApp - Application ya kisasa inayochanganya Java Swing na AWT.
 * Inajumuisha:
 * 1. Interactive Drawing Canvas (AWT Graphics2D & Mouse Listeners)
 * 2. Record & Task Manager (Swing JTable, Form controls & AWT Layouts)
 * 3. System Control Panel (Interactive Swing components & Animated Timers)
 * 4. Code & Text Editor (Text area, word counter, AWT Key Listeners)
 */
public class SwingAwtApp extends JFrame {

    // Palette ya rangi (AWT Color objects kwa ajili ya Modern Dark Theme)
    private static final Color BG_DARK = new Color(30, 30, 46);
    private static final Color PANEL_BG = new Color(40, 40, 60);
    private static final Color ACCENT_BLUE = new Color(116, 199, 236);
    private static final Color ACCENT_PURPLE = new Color(203, 166, 247);
    private static final Color ACCENT_GREEN = new Color(166, 227, 161);
    private static final Color ACCENT_RED = new Color(243, 139, 168);
    private static final Color TEXT_COLOR = new Color(205, 214, 244);
    private static final Color TEXT_MUTED = new Color(147, 153, 178);

    // Component za Header
    private JLabel clockLabel;
    private JLabel statusLabel;

    public SwingAwtApp() {
        // Kuweka Title ya Window na Size
        setTitle("Java Swing & AWT Interactive Workspace");
        setSize(1100, 750);
        setMinimumSize(new Dimension(900, 600));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Centered screen

        // Kutumia AWT BorderLayout kwa Frame kuu
        setLayout(new BorderLayout(10, 10));
        getContentPane().setBackground(BG_DARK);

        // Kuunda Header Bar (AWT Panel)
        add(createHeaderPanel(), BorderLayout.NORTH);

        // Kuunda Tabbed Pane kwa ajili ya sehemu mbalimbali za app
        JTabbedPane tabbedPane = createCustomTabbedPane();
        tabbedPane.addTab("🎨 Paint & Canvas (AWT)", createDrawingTab());
        tabbedPane.addTab("📊 Task Manager (Swing Table)", createTableTab());
        tabbedPane.addTab("⚙️ Control Panel & Widgets", createControlsTab());
        tabbedPane.addTab("📝 Quick Notepad", createNotepadTab());

        add(tabbedPane, BorderLayout.CENTER);

        // Kuunda Footer Bar (AWT Panel)
        add(createFooterPanel(), BorderLayout.SOUTH);

        // Kuanzisha Clock Timer
        startClockTimer();
    }

    private JPanel createHeaderPanel() {
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(PANEL_BG);
        header.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(new Color(60, 60, 80), 1),
            new EmptyBorder(12, 20, 12, 20)
        ));

        // App Title (AWT Font & Color)
        JLabel titleLabel = new JLabel("⚡ JAVA SWING + AWT WORKSPACE");
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 18));
        titleLabel.setForeground(ACCENT_BLUE);

        // Realtime Clock Label
        clockLabel = new JLabel();
        clockLabel.setFont(new Font("Monospaced", Font.BOLD, 14));
        clockLabel.setForeground(TEXT_COLOR);

        header.add(titleLabel, BorderLayout.WEST);
        header.add(clockLabel, BorderLayout.EAST);

        return header;
    }

    private JTabbedPane createCustomTabbedPane() {
        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.setFont(new Font("SansSerif", Font.BOLD, 13));
        tabbedPane.setBackground(PANEL_BG);
        tabbedPane.setForeground(TEXT_COLOR);
        return tabbedPane;
    }

    private JPanel createDrawingTab() {
        JPanel mainTab = new JPanel(new BorderLayout(10, 10));
        mainTab.setBackground(BG_DARK);
        mainTab.setBorder(new EmptyBorder(10, 10, 10, 10));

        // Custom AWT Canvas Panel
        DrawingPanel canvas = new DrawingPanel();

        // Toolbar kwa ajili ya kuchagua Tool na Rangi (AWT FlowLayout)
        JPanel toolbar = new JPanel(new FlowLayout(FlowLayout.LEFT, 12, 8));
        toolbar.setBackground(PANEL_BG);
        toolbar.setBorder(new LineBorder(new Color(60, 60, 80)));

        JLabel toolLabel = new JLabel("Tool:");
        toolLabel.setForeground(TEXT_COLOR);
        
        JComboBox<String> toolBox = new JComboBox<>(new String[]{"Free Brush", "Line", "Rectangle", "Oval", "Eraser"});
        toolBox.addActionListener(e -> canvas.setCurrentTool((String) toolBox.getSelectedItem()));

        JLabel sizeLabel = new JLabel("Size:");
        sizeLabel.setForeground(TEXT_COLOR);
        JSlider sizeSlider = new JSlider(1, 30, 4);
        sizeSlider.setOpaque(false);
        sizeSlider.setPreferredSize(new Dimension(100, 20));
        sizeSlider.addChangeListener(e -> canvas.setBrushSize(sizeSlider.getValue()));

        JButton colorBtn = new JButton("Select Color");
        colorBtn.setBackground(ACCENT_PURPLE);
        colorBtn.setForeground(Color.BLACK);
        colorBtn.setFocusPainted(false);
        colorBtn.addActionListener(e -> {
            Color chosen = JColorChooser.showDialog(this, "Pick Brush Color", canvas.getBrushColor());
            if (chosen != null) {
                canvas.setBrushColor(chosen);
                colorBtn.setBackground(chosen);
            }
        });

        JButton clearBtn = new JButton("Clear Canvas");
        clearBtn.setBackground(ACCENT_RED);
        clearBtn.setForeground(Color.BLACK);
        clearBtn.setFocusPainted(false);
        clearBtn.addActionListener(e -> canvas.clearCanvas());

        toolbar.add(toolLabel);
        toolbar.add(toolBox);
        toolbar.add(sizeLabel);
        toolbar.add(sizeSlider);
        toolbar.add(colorBtn);
        toolbar.add(clearBtn);

        mainTab.add(toolbar, BorderLayout.NORTH);
        mainTab.add(canvas, BorderLayout.CENTER);

        return mainTab;
    }

    // Custom Inner Panel inayotumia AWT Graphics2D na Event Listeners
    private class DrawingPanel extends JPanel implements MouseListener, MouseMotionListener {

        private Color currentColor = ACCENT_BLUE;
        private int brushSize = 4;
        private String currentTool = "Free Brush";

        private Point startPoint = null;
        private Point currentPoint = null;

        // Custom Shape data struct
        private static class DrawShape {
            String type;
            Color color;
            int size;
            Point start;
            Point end;
            List<Point> points; // Kwa ajili ya Free Brush

            DrawShape(String type, Color color, int size, Point start, Point end) {
                this.type = type;
                this.color = color;
                this.size = size;
                this.start = start;
                this.end = end;
            }

            DrawShape(Color color, int size, List<Point> points) {
                this.type = "Free Brush";
                this.color = color;
                this.size = size;
                this.points = new ArrayList<>(points);
            }
        }

        private final List<DrawShape> shapes = new ArrayList<>();
        private List<Point> activeStroke = new ArrayList<>();

        public DrawingPanel() {
            setBackground(new Color(20, 20, 30));
            setBorder(new LineBorder(new Color(60, 60, 80)));
            addMouseListener(this);
            addMouseMotionListener(this);
        }

        public void setBrushColor(Color c) { this.currentColor = c; }
        public Color getBrushColor() { return currentColor; }
        public void setBrushSize(int size) { this.brushSize = size; }
        public void setCurrentTool(String tool) { this.currentTool = tool; }

        public void clearCanvas() {
            shapes.clear();
            activeStroke.clear();
            repaint();
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D) g;

            // Kuweka Antialiasing ya AWT ili michoro iwe laini sana
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            // Kuchora michoro yote iliyopo
            for (DrawShape shape : shapes) {
                g2.setColor(shape.color);
                g2.setStroke(new BasicStroke(shape.size, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));

                if ("Free Brush".equals(shape.type) && shape.points != null) {
                    for (int i = 0; i < shape.points.size() - 1; i++) {
                        Point p1 = shape.points.get(i);
                        Point p2 = shape.points.get(i + 1);
                        g2.drawLine(p1.x, p1.y, p2.x, p2.y);
                    }
                } else if ("Line".equals(shape.type) && shape.start != null && shape.end != null) {
                    g2.drawLine(shape.start.x, shape.start.y, shape.end.x, shape.end.y);
                } else if ("Rectangle".equals(shape.type) && shape.start != null && shape.end != null) {
                    int x = Math.min(shape.start.x, shape.end.x);
                    int y = Math.min(shape.start.y, shape.end.y);
                    int w = Math.abs(shape.start.x - shape.end.x);
                    int h = Math.abs(shape.start.y - shape.end.y);
                    g2.drawRect(x, y, w, h);
                } else if ("Oval".equals(shape.type) && shape.start != null && shape.end != null) {
                    int x = Math.min(shape.start.x, shape.end.x);
                    int y = Math.min(shape.start.y, shape.end.y);
                    int w = Math.abs(shape.start.x - shape.end.x);
                    int h = Math.abs(shape.start.y - shape.end.y);
                    g2.drawOval(x, y, w, h);
                }
            }

            // Kuchora live preview wakati mtumiaji anavuta mouse (Dragging)
            if (startPoint != null && currentPoint != null) {
                g2.setColor(currentTool.equals("Eraser") ? new Color(20, 20, 30) : currentColor);
                g2.setStroke(new BasicStroke(brushSize, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));

                if ("Free Brush".equals(currentTool) || "Eraser".equals(currentTool)) {
                    for (int i = 0; i < activeStroke.size() - 1; i++) {
                        Point p1 = activeStroke.get(i);
                        Point p2 = activeStroke.get(i + 1);
                        g2.drawLine(p1.x, p1.y, p2.x, p2.y);
                    }
                } else if ("Line".equals(currentTool)) {
                    g2.drawLine(startPoint.x, startPoint.y, currentPoint.x, currentPoint.y);
                } else if ("Rectangle".equals(currentTool)) {
                    int x = Math.min(startPoint.x, currentPoint.x);
                    int y = Math.min(startPoint.y, currentPoint.y);
                    int w = Math.abs(startPoint.x - currentPoint.x);
                    int h = Math.abs(startPoint.y - currentPoint.y);
                    g2.drawRect(x, y, w, h);
                } else if ("Oval".equals(currentTool)) {
                    int x = Math.min(startPoint.x, currentPoint.x);
                    int y = Math.min(startPoint.y, currentPoint.y);
                    int w = Math.abs(startPoint.x - currentPoint.x);
                    int h = Math.abs(startPoint.y - currentPoint.y);
                    g2.drawOval(x, y, w, h);
                }
            }
        }

        @Override
        public void mousePressed(MouseEvent e) {
            startPoint = e.getPoint();
            currentPoint = e.getPoint();
            if ("Free Brush".equals(currentTool) || "Eraser".equals(currentTool)) {
                activeStroke.clear();
                activeStroke.add(startPoint);
            }
        }

        @Override
        public void mouseDragged(MouseEvent e) {
            currentPoint = e.getPoint();
            if ("Free Brush".equals(currentTool) || "Eraser".equals(currentTool)) {
                activeStroke.add(currentPoint);
            }
            repaint();
        }

        @Override
        public void mouseReleased(MouseEvent e) {
            currentPoint = e.getPoint();
            Color activeColor = currentTool.equals("Eraser") ? new Color(20, 20, 30) : currentColor;

            if ("Free Brush".equals(currentTool) || "Eraser".equals(currentTool)) {
                activeStroke.add(currentPoint);
                shapes.add(new DrawShape(activeColor, brushSize, activeStroke));
                activeStroke = new ArrayList<>();
            } else {
                shapes.add(new DrawShape(currentTool, activeColor, brushSize, startPoint, currentPoint));
            }

            startPoint = null;
            currentPoint = null;
            repaint();
        }

        @Override public void mouseClicked(MouseEvent e) {}
        @Override public void mouseEntered(MouseEvent e) {}
        @Override public void mouseExited(MouseEvent e) {}
        @Override public void mouseMoved(MouseEvent e) {}
    }

    private JPanel createTableTab() {
        JPanel tab = new JPanel(new BorderLayout(10, 10));
        tab.setBackground(BG_DARK);
        tab.setBorder(new EmptyBorder(10, 10, 10, 10));

        // Model ya JTable
        String[] columns = {"ID", "Kipengele / Task", "Aina (Category)", "Hali (Status)", "Prioriti"};
        DefaultTableModel tableModel = new DefaultTableModel(columns, 0);

        // Data za mfano
        tableModel.addRow(new Object[]{"101", "Kutengeneza UI ya Swing", "Java Design", "Inaendelea", "High"});
        tableModel.addRow(new Object[]{"102", "Kuunganisha AWT Listener", "Backend/Event", "Imekamilika", "Medium"});
        tableModel.addRow(new Object[]{"103", "Kukagua Graphics2D", "Drawing Module", "Bado", "High"});
        tableModel.addRow(new Object[]{"104", "Testing App Performance", "QA", "Inaendelea", "Low"});

        JTable table = new JTable(tableModel);
        table.setBackground(PANEL_BG);
        table.setForeground(TEXT_COLOR);
        table.setGridColor(new Color(60, 60, 80));
        table.setRowHeight(28);
        table.getTableHeader().setFont(new Font("SansSerif", Font.BOLD, 12));
        table.getTableHeader().setBackground(new Color(50, 50, 75));
        table.getTableHeader().setForeground(ACCENT_BLUE);

        // Kutumia TableRowSorter kwa ajili ya Live Search Filter
        TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(tableModel);
        table.setRowSorter(sorter);

        // Panel ya Form ya kuongeza Data mpya (AWT GridBagLayout)
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(PANEL_BG);
        formPanel.setBorder(BorderFactory.createTitledBorder(
            new LineBorder(ACCENT_BLUE), "Ongeza Task Mpya", TitledBorder.LEFT, TitledBorder.TOP,
            new Font("SansSerif", Font.BOLD, 12), ACCENT_BLUE
        ));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(6, 6, 6, 6);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JTextField txtId = new JTextField(8);
        JTextField txtTask = new JTextField(15);
        JComboBox<String> cbCategory = new JComboBox<>(new String[]{"Java Design", "Backend/Event", "Database", "QA"});
        JComboBox<String> cbStatus = new JComboBox<>(new String[]{"Bado", "Inaendelea", "Imekamilika"});
        JComboBox<String> cbPriority = new JComboBox<>(new String[]{"High", "Medium", "Low"});

        // Label na Input Fields
        gbc.gridx = 0; gbc.gridy = 0;
        formPanel.add(new JLabel("ID:"), gbc);
        gbc.gridx = 1;
        formPanel.add(txtId, gbc);

        gbc.gridx = 2; gbc.gridy = 0;
        formPanel.add(new JLabel("Task:"), gbc);
        gbc.gridx = 3;
        formPanel.add(txtTask, gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        formPanel.add(new JLabel("Aina:"), gbc);
        gbc.gridx = 1;
        formPanel.add(cbCategory, gbc);

        gbc.gridx = 2; gbc.gridy = 1;
        formPanel.add(new JLabel("Hali:"), gbc);
        gbc.gridx = 3;
        formPanel.add(cbStatus, gbc);

        gbc.gridx = 0; gbc.gridy = 2;
        formPanel.add(new JLabel("Prioriti:"), gbc);
        gbc.gridx = 1;
        formPanel.add(cbPriority, gbc);

        // Buttons Action
        JButton btnAdd = new JButton("➕ Ongeza");
        btnAdd.setBackground(ACCENT_GREEN);
        btnAdd.setForeground(Color.BLACK);
        btnAdd.addActionListener(e -> {
            if (txtId.getText().trim().isEmpty() || txtTask.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Tafadhali jaza ID na Task Name!", "Kosa", JOptionPane.ERROR_MESSAGE);
                return;
            }
            tableModel.addRow(new Object[]{
                txtId.getText().trim(),
                txtTask.getText().trim(),
                cbCategory.getSelectedItem(),
                cbStatus.getSelectedItem(),
                cbPriority.getSelectedItem()
            });
            txtId.setText("");
            txtTask.setText("");
            updateStatus("Task mpya imeongezwa kikamilifu!");
        });

        JButton btnDelete = new JButton("🗑️ Futa Iliyochaguliwa");
        btnDelete.setBackground(ACCENT_RED);
        btnDelete.setForeground(Color.BLACK);
        btnDelete.addActionListener(e -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow >= 0) {
                int modelRow = table.convertRowIndexToModel(selectedRow);
                tableModel.removeRow(modelRow);
                updateStatus("Task imefutwa!");
            } else {
                JOptionPane.showMessageDialog(this, "Chagua mstari wa kufuta!", "Taarifa", JOptionPane.WARNING_MESSAGE);
            }
        });

        gbc.gridx = 2; gbc.gridy = 2;
        formPanel.add(btnAdd, gbc);
        gbc.gridx = 3;
        formPanel.add(btnDelete, gbc);

        // Search Bar Panel (North Panel)
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        searchPanel.setBackground(BG_DARK);
        JLabel searchLabel = new JLabel("🔍 Taafuta: ");
        searchLabel.setForeground(TEXT_COLOR);
        JTextField txtSearch = new JTextField(15);
        txtSearch.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                String text = txtSearch.getText();
                if (text.trim().length() == 0) {
                    sorter.setRowFilter(null);
                } else {
                    sorter.setRowFilter(RowFilter.regexFilter("(?i)" + text));
                }
            }
        });
        searchPanel.add(searchLabel);
        searchPanel.add(txtSearch);

        JPanel topContainer = new JPanel(new BorderLayout());
        topContainer.setBackground(BG_DARK);
        topContainer.add(searchPanel, BorderLayout.NORTH);
        topContainer.add(formPanel, BorderLayout.SOUTH);

        tab.add(topContainer, BorderLayout.NORTH);
        tab.add(new JScrollPane(table), BorderLayout.CENTER);

        return tab;
    }

    private JPanel createControlsTab() {
        JPanel tab = new JPanel(new GridLayout(2, 2, 15, 15));
        tab.setBackground(BG_DARK);
        tab.setBorder(new EmptyBorder(15, 15, 15, 15));

        // Panel 1: Progress & Timers
        JPanel p1 = createCardPanel(" Progress Bar & Live Animation");
        JProgressBar progressBar = new JProgressBar(0, 100);
        progressBar.setValue(35);
        progressBar.setStringPainted(true);
        progressBar.setForeground(ACCENT_BLUE);

        JButton btnStartProgress = new JButton("Anza Animation Timer");
        btnStartProgress.setBackground(ACCENT_PURPLE);
        btnStartProgress.setForeground(Color.BLACK);
        
        Timer progressTimer = new Timer(50, null);
        progressTimer.addActionListener(e -> {
            int val = progressBar.getValue();
            if (val >= 100) {
                progressTimer.stop();
                btnStartProgress.setEnabled(true);
                updateStatus("Animation imekamilika!");
            } else {
                progressBar.setValue(val + 1);
            }
        });

        btnStartProgress.addActionListener(e -> {
            progressBar.setValue(0);
            btnStartProgress.setEnabled(false);
            progressTimer.start();
            updateStatus("Animation inaendesha progress bar...");
        });

        p1.add(new JLabel("Mfano wa JProgressBar na javax.swing.Timer:"));
        p1.add(progressBar);
        p1.add(btnStartProgress);

        // Panel 2: Interactive Controls (Sliders & Switches)
        JPanel p2 = createCardPanel("🎛️ Sliders & Radio Options");
        JSlider valueSlider = new JSlider(0, 100, 50);
        valueSlider.setMajorTickSpacing(25);
        valueSlider.setPaintTicks(true);
        valueSlider.setPaintLabels(true);

        JLabel sliderValLabel = new JLabel("Value: 50");
        sliderValLabel.setForeground(ACCENT_BLUE);
        valueSlider.addChangeListener(e -> sliderValLabel.setText("Value: " + valueSlider.getValue()));

        JRadioButton rb1 = new JRadioButton("Option A (AWT Layout)", true);
        JRadioButton rb2 = new JRadioButton("Option B (Swing Modern UI)");
        ButtonGroup group = new ButtonGroup();
        group.add(rb1); group.add(rb2);

        p2.add(sliderValLabel);
        p2.add(valueSlider);
        p2.add(rb1);
        p2.add(rb2);

        // Panel 3: Dialog Alerts & Messages
        JPanel p3 = createCardPanel("🔔 Dialogs & Messages");
        JButton btnInfo = new JButton("Onyesha Info Dialog");
        btnInfo.addActionListener(e -> JOptionPane.showMessageDialog(this, "Huu ni mfano wa JOptionPane Info Dialog!", "Taarifa", JOptionPane.INFORMATION_MESSAGE));

        JButton btnInput = new JButton("Onyesha Input Dialog");
        btnInput.addActionListener(e -> {
            String name = JOptionPane.showInputDialog(this, "Unaitwa nani?");
            if (name != null && !name.trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Habari yako, " + name + "!");
            }
        });

        p3.add(btnInfo);
        p3.add(btnInput);

        // Panel 4: System Information Card
        JPanel p4 = createCardPanel("💻 System & Runtime Info");
        JLabel infoOs = new JLabel("OS Name: " + System.getProperty("os.name"));
        JLabel infoJava = new JLabel("Java Version: " + System.getProperty("java.version"));
        JLabel infoArch = new JLabel("Architecture: " + System.getProperty("os.arch"));

        p4.add(infoOs);
        p4.add(infoJava);
        p4.add(infoArch);

        tab.add(p1);
        tab.add(p2);
        tab.add(p3);
        tab.add(p4);

        return tab;
    }

    private JPanel createCardPanel(String title) {
        JPanel p = new JPanel();
        p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));
        p.setBackground(PANEL_BG);
        p.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(new Color(60, 60, 80), 1),
            new EmptyBorder(12, 12, 12, 12)
        ));

        JLabel titleLbl = new JLabel(title);
        titleLbl.setFont(new Font("SansSerif", Font.BOLD, 14));
        titleLbl.setForeground(ACCENT_BLUE);
        titleLbl.setAlignmentX(Component.LEFT_ALIGNMENT);

        p.add(titleLbl);
        p.add(Box.createVerticalStrut(10));
        return p;
    }

    private JPanel createNotepadTab() {
        JPanel tab = new JPanel(new BorderLayout(10, 10));
        tab.setBackground(BG_DARK);
        tab.setBorder(new EmptyBorder(10, 10, 10, 10));

        JTextArea textArea = new JTextArea();
        textArea.setBackground(PANEL_BG);
        textArea.setForeground(TEXT_COLOR);
        textArea.setCaretColor(ACCENT_BLUE);
        textArea.setFont(new Font("Monospaced", Font.PLAIN, 14));
        textArea.setText("// Andika au uweke maelezo yako hapa...\nSystem hii inatengenezwa kikamilifu kwa Java AWT na Swing!");

        JLabel statsLabel = new JLabel("Maneno: 0 | Herufi: 0");
        statsLabel.setForeground(TEXT_MUTED);

        // KeyListener ya AWT kufuatilia idadi ya maneno na herufi muda huo huo
        textArea.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                String text = textArea.getText().trim();
                int chars = textArea.getText().length();
                int words = text.isEmpty() ? 0 : text.split("\\s+").length;
                statsLabel.setText("Maneno: " + words + " | Herufi: " + chars);
            }
        });

        JPanel toolBar = new JPanel(new FlowLayout(FlowLayout.LEFT));
        toolBar.setBackground(PANEL_BG);

        JButton btnClear = new JButton("Safisha Notepad");
        btnClear.setBackground(ACCENT_RED);
        btnClear.setForeground(Color.BLACK);
        btnClear.addActionListener(e -> {
            textArea.setText("");
            statsLabel.setText("Maneno: 0 | Herufi: 0");
            updateStatus("Notepad imesafishwa.");
        });

        toolBar.add(btnClear);
        toolBar.add(statsLabel);

        tab.add(toolBar, BorderLayout.NORTH);
        tab.add(new JScrollPane(textArea), BorderLayout.CENTER);

        return tab;
    }

    private JPanel createFooterPanel() {
        JPanel footer = new JPanel(new BorderLayout());
        footer.setBackground(PANEL_BG);
        footer.setBorder(new EmptyBorder(6, 15, 6, 15));

        statusLabel = new JLabel("Tayari (System is Running)");
        statusLabel.setFont(new Font("SansSerif", Font.PLAIN, 12));
        statusLabel.setForeground(ACCENT_GREEN);

        JLabel authorLabel = new JLabel("Java AWT & Swing Suite © 2026");
        authorLabel.setFont(new Font("SansSerif", Font.ITALIC, 11));
        authorLabel.setForeground(TEXT_MUTED);

        footer.add(statusLabel, BorderLayout.WEST);
        footer.add(authorLabel, BorderLayout.EAST);

        return footer;
    }

    private void updateStatus(String msg) {
        if (statusLabel != null) {
            statusLabel.setText("Status: " + msg);
        }
    }

    private void startClockTimer() {
        Timer clockTimer = new Timer(1000, new ActionListener() {
            private final SimpleDateFormat sdf = new SimpleDateFormat("EEE, dd MMM yyyy - HH:mm:ss");
            @Override
            public void actionPerformed(ActionEvent e) {
                clockLabel.setText(sdf.format(new Date()));
            }
        });
        clockTimer.start();
    }

    public static void main(String[] args) {
        // Kuweka Look & Feel kulingana na mfumo au CrossPlatform
        try {
            UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
        } catch (Exception ignored) {}

        // Kuanzisha GUI kwenye Event Dispatch Thread (EDT)
        SwingUtilities.invokeLater(() -> {
            SwingAwtApp app = new SwingAwtApp();
            app.setVisible(true);
        });
    }
}