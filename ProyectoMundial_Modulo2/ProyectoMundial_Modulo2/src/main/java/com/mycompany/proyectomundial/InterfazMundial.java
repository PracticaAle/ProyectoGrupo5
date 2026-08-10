package com.mycompany.proyectomundial;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.RenderingHints;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;

/**
 * Interfaz grafica principal del proyecto Mundial.
 * Esta clase permite usar el proyecto con botones y una ventana Swing.
 */
public class InterfazMundial extends JFrame {

    // Arreglos principales del torneo.
    private Equipo[] equipos;
    private CuerpoArbitral[] arbitros;
    private Sede[] sedes;
    private Grupo[] grupos;
    private Partido[] calendario;

    // Cantidad inicial de equipos.
    private int capacidad = 32;

    // Etiquetas de las tarjetas superiores.
    private JLabel equiposValor;
    private JLabel gruposValor;
    private JLabel partidosValor;
    private JLabel campeonValor;

    // Area donde se muestran resultados.
    private JTextArea salida;

    // Selector para elegir cantidad de equipos.
    private JComboBox<String> selectorCapacidad;

    /**
     * Constructor de la ventana.
     */
    public InterfazMundial() {
        configurarVentana();
        inicializarDatos();
        construirInterfaz();
        actualizarPanelEstado();
    }

    /**
     * Metodo main para ejecutar esta interfaz.
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new InterfazMundial().setVisible(true);
            }
        });
    }

    /**
     * Configura titulo, tamano y cierre de la ventana.
     */
    private void configurarVentana() {
        setTitle("Proyecto Mundial - Panel de Control");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1120, 720);
        setMinimumSize(new Dimension(980, 640));
        setLocationRelativeTo(null);
    }

    /**
     * Crea los arreglos vacios para iniciar un torneo.
     */
    private void inicializarDatos() {
        equipos = new Equipo[capacidad];
        arbitros = new CuerpoArbitral[30];
        sedes = new Sede[6];
        grupos = null;
        calendario = null;
    }

    /**
     * Construye toda la pantalla principal.
     */
    private void construirInterfaz() {
        JPanel raiz = new JPanel(new BorderLayout());
        raiz.setBackground(new Color(244, 247, 251));

        raiz.add(crearEncabezado(), BorderLayout.NORTH);
        raiz.add(crearMenuLateral(), BorderLayout.WEST);
        raiz.add(crearContenido(), BorderLayout.CENTER);

        setContentPane(raiz);
    }

    /**
     * Crea el encabezado superior decorado.
     */
    private JPanel crearEncabezado() {
        JPanel panel = new JPanel(new BorderLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);

                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                g2.setColor(new Color(12, 35, 64));
                g2.fillRect(0, 0, getWidth(), getHeight());

                g2.setColor(new Color(16, 95, 111));
                g2.fillOval(getWidth() - 260, -90, 360, 230);

                g2.setColor(new Color(198, 161, 91));
                g2.fillOval(getWidth() - 120, 55, 170, 170);
            }
        };

        panel.setPreferredSize(new Dimension(0, 120));
        panel.setBorder(new EmptyBorder(22, 28, 18, 28));

        JLabel titulo = new JLabel("Proyecto Mundial");
        titulo.setForeground(Color.WHITE);
        titulo.setFont(new Font("SansSerif", Font.BOLD, 34));

        JLabel subtitulo = new JLabel("Panel grafico para gestionar equipos, grupos, calendario y eliminatorias");
        subtitulo.setForeground(new Color(218, 231, 238));
        subtitulo.setFont(new Font("SansSerif", Font.PLAIN, 15));

        JPanel textos = new JPanel(new GridLayout(2, 1));
        textos.setOpaque(false);
        textos.add(titulo);
        textos.add(subtitulo);

        JLabel modulo = new JLabel("MUNDIAL 2026");
        modulo.setHorizontalAlignment(SwingConstants.RIGHT);
        modulo.setForeground(new Color(247, 224, 155));
        modulo.setFont(new Font("SansSerif", Font.BOLD, 18));

        panel.add(textos, BorderLayout.WEST);
        panel.add(modulo, BorderLayout.EAST);

        return panel;
    }

    /**
     * Crea el menu lateral con todos los botones.
     */
    private JPanel crearMenuLateral() {
        JPanel menu = new JPanel();

        // GridLayout con 0 filas permite agregar todos los botones necesarios.
        menu.setLayout(new GridLayout(0, 1, 0, 8));
        menu.setPreferredSize(new Dimension(260, 0));
        menu.setBackground(new Color(19, 42, 69));
        menu.setBorder(new EmptyBorder(18, 18, 18, 18));

        selectorCapacidad = new JComboBox<String>(new String[]{"24 equipos", "32 equipos", "48 equipos", "64 equipos"});
        selectorCapacidad.setSelectedIndex(1);
        selectorCapacidad.setFont(new Font("SansSerif", Font.BOLD, 14));
        selectorCapacidad.setFocusable(false);

        menu.add(crearEtiquetaMenu("Configuracion"));
        menu.add(selectorCapacidad);

        menu.add(crearBoton("Nuevo torneo", new Runnable() {
            @Override
            public void run() {
                nuevoTorneo();
            }
        }));

        menu.add(crearEtiquetaMenu("Registro manual"));

        menu.add(crearBoton("Agregar equipo", new Runnable() {
            @Override
            public void run() {
                agregarEquipoManual();
            }
        }));

        menu.add(crearBoton("Agregar arbitro", new Runnable() {
            @Override
            public void run() {
                agregarArbitroManual();
            }
        }));

        menu.add(crearBoton("Agregar sede", new Runnable() {
            @Override
            public void run() {
                agregarSedeManual();
            }
        }));

        menu.add(crearEtiquetaMenu("Simulacion"));

        menu.add(crearBoton("Cargar modo demo", new Runnable() {
            @Override
            public void run() {
                cargarDemo();
            }
        }));

        menu.add(crearBoton("Crear grupos", new Runnable() {
            @Override
            public void run() {
                crearGrupos();
            }
        }));

        menu.add(crearBoton("Generar calendario", new Runnable() {
            @Override
            public void run() {
                generarCalendario();
            }
        }));

        menu.add(crearBoton("Simular fase grupos", new Runnable() {
            @Override
            public void run() {
                simularGrupos();
            }
        }));

        menu.add(crearBoton("Crear llaves", new Runnable() {
            @Override
            public void run() {
                crearLlaves();
            }
        }));

        menu.add(crearBoton("Simular eliminatoria", new Runnable() {
            @Override
            public void run() {
                simularEliminatoria();
            }
        }));

        menu.add(crearEtiquetaMenu("Consultas"));

        menu.add(crearBoton("Ver grupos", new Runnable() {
            @Override
            public void run() {
                mostrarGruposEnPantalla();
            }
        }));

        menu.add(crearBoton("Ver tabla", new Runnable() {
            @Override
            public void run() {
                mostrarTablaEnPantalla();
            }
        }));

        menu.add(crearBoton("Resumen final", new Runnable() {
            @Override
            public void run() {
                mostrarResumenFinal();
            }
        }));

        return menu;
    }

    /**
     * Crea una etiqueta para separar secciones del menu.
     */
    private JLabel crearEtiquetaMenu(String texto) {
        JLabel etiqueta = new JLabel(texto.toUpperCase());
        etiqueta.setForeground(new Color(174, 192, 207));
        etiqueta.setFont(new Font("SansSerif", Font.BOLD, 12));
        return etiqueta;
    }

    /**
     * Crea un boton con estilo.
     */
    private JButton crearBoton(String texto, final Runnable accion) {
        JButton boton = new JButton(texto);
        boton.setFocusPainted(false);
        boton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        boton.setBackground(new Color(34, 77, 105));
        boton.setForeground(Color.WHITE);
        boton.setFont(new Font("SansSerif", Font.BOLD, 13));
        boton.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(62, 113, 139)),
                new EmptyBorder(8, 12, 8, 12)
        ));

        // Ejecuta la accion cuando se presiona el boton.
        boton.addActionListener(e -> accion.run());

        return boton;
    }

    /**
     * Crea el area central de la interfaz.
     */
    private JPanel crearContenido() {
        JPanel contenido = new JPanel(new BorderLayout(18, 18));
        contenido.setBackground(new Color(244, 247, 251));
        contenido.setBorder(new EmptyBorder(22, 22, 22, 22));

        contenido.add(crearPanelEstado(), BorderLayout.NORTH);

        salida = new JTextArea();
        salida.setEditable(false);
        salida.setLineWrap(true);
        salida.setWrapStyleWord(true);
        salida.setFont(new Font("Monospaced", Font.PLAIN, 14));
        salida.setForeground(new Color(28, 38, 52));
        salida.setBackground(Color.WHITE);
        salida.setBorder(new EmptyBorder(18, 18, 18, 18));

        salida.setText("Bienvenido al panel grafico del Mundial.\n\n"
                + "Puedes usar el registro manual o cargar el modo demo.\n\n"
                + "Flujo recomendado:\n"
                + "1. Agregar equipos, arbitros y sedes, o usar modo demo\n"
                + "2. Crear grupos\n"
                + "3. Generar calendario\n"
                + "4. Simular fase grupos\n"
                + "5. Crear llaves\n"
                + "6. Simular eliminatoria\n"
                + "7. Resumen final\n");

        JScrollPane scroll = new JScrollPane(salida);
        scroll.setBorder(BorderFactory.createLineBorder(new Color(218, 226, 235)));

        contenido.add(scroll, BorderLayout.CENTER);
        contenido.add(crearPie(), BorderLayout.SOUTH);

        return contenido;
    }

    /**
     * Crea las tarjetas superiores de estado.
     */
    private JPanel crearPanelEstado() {
        JPanel panel = new JPanel(new GridLayout(1, 4, 14, 0));
        panel.setOpaque(false);

        equiposValor = crearValorTarjeta();
        gruposValor = crearValorTarjeta();
        partidosValor = crearValorTarjeta();
        campeonValor = crearValorTarjeta();

        panel.add(crearTarjeta("Equipos", equiposValor, new Color(10, 83, 113)));
        panel.add(crearTarjeta("Grupos", gruposValor, new Color(25, 116, 101)));
        panel.add(crearTarjeta("Partidos jugados", partidosValor, new Color(126, 86, 35)));
        panel.add(crearTarjeta("Campeon", campeonValor, new Color(116, 48, 64)));

        return panel;
    }

    /**
     * Crea el texto grande de cada tarjeta.
     */
    private JLabel crearValorTarjeta() {
        JLabel valor = new JLabel("-");
        valor.setForeground(new Color(20, 30, 44));
        valor.setFont(new Font("SansSerif", Font.BOLD, 24));
        return valor;
    }

    /**
     * Crea una tarjeta visual superior.
     */
    private JPanel crearTarjeta(String titulo, JLabel valor, Color acento) {
        JPanel tarjeta = new JPanel(new BorderLayout());
        tarjeta.setBackground(Color.WHITE);
        tarjeta.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(0, 5, 0, 0, acento),
                new EmptyBorder(16, 18, 16, 18)
        ));

        JLabel etiqueta = new JLabel(titulo);
        etiqueta.setForeground(new Color(103, 116, 132));
        etiqueta.setFont(new Font("SansSerif", Font.BOLD, 13));

        tarjeta.add(etiqueta, BorderLayout.NORTH);
        tarjeta.add(valor, BorderLayout.CENTER);

        return tarjeta;
    }

    /**
     * Crea el texto inferior de la ventana.
     */
    private JPanel crearPie() {
        JPanel pie = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        pie.setOpaque(false);

        JLabel texto = new JLabel("Interfaz Swing creada para el proyecto existente");
        texto.setForeground(new Color(106, 118, 132));
        texto.setFont(new Font("SansSerif", Font.PLAIN, 12));

        pie.add(texto);

        return pie;
    }

    /**
     * Reinicia el torneo segun la cantidad seleccionada.
     */
    private void nuevoTorneo() {
        int indice = selectorCapacidad.getSelectedIndex();

        if (indice == 0) {
            capacidad = 24;
        } else if (indice == 1) {
            capacidad = 32;
        } else if (indice == 2) {
            capacidad = 48;
        } else {
            capacidad = 64;
        }

        inicializarDatos();
        actualizarPanelEstado();

        salida.setText("Nuevo torneo creado con capacidad para " + capacidad + " equipos.\n"
                + "Ahora puedes registrar datos manualmente o cargar modo demo.\n");
    }

    /**
     * Permite agregar un equipo usando el metodo existente del proyecto.
     */
    private void agregarEquipoManual() {
        Equipo.agregarEquipo(equipos);
        actualizarPanelEstado();

        salida.setText("Registro manual de equipo finalizado.\n"
                + "Equipos registrados: " + contarEquipos() + " de " + equipos.length + "\n");
    }

    /**
     * Permite agregar un arbitro usando el metodo existente del proyecto.
     */
    private void agregarArbitroManual() {
        CuerpoArbitral.agregarArbitro(arbitros);
        actualizarPanelEstado();

        salida.setText("Registro manual de arbitro finalizado.\n"
                + "Arbitros registrados: " + contarArbitros() + " de " + arbitros.length + "\n");
    }

    /**
     * Permite agregar una sede usando el metodo existente del proyecto.
     */
    private void agregarSedeManual() {
        Sede.agregarSede(sedes);
        actualizarPanelEstado();

        salida.setText("Registro manual de sede finalizado.\n"
                + "Sedes registradas: " + contarSedes() + " de " + sedes.length + "\n");
    }

    /**
     * Carga automaticamente equipos, arbitros y sedes.
     */
    private void cargarDemo() {
        Equipo.modoDemoEquipos(equipos);
        CuerpoArbitral.modoDemoArbitros(arbitros);
        Sede.modoDemoSedes(sedes);

        actualizarPanelEstado();

        salida.setText("Modo demo cargado correctamente.\n\n"
                + "Equipos registrados: " + contarEquipos() + "\n"
                + "Arbitros registrados: " + contarArbitros() + "\n"
                + "Sedes registradas: " + contarSedes() + "\n");
    }

    /**
     * Crea los grupos del torneo.
     */
    private void crearGrupos() {
        grupos = ModuloGrupos.crearGrupos(equipos);
        actualizarPanelEstado();

        if (grupos != null) {
            mostrarGruposEnPantalla();
        }
    }

    /**
     * Genera el calendario de fase de grupos.
     */
    private void generarCalendario() {
        calendario = ModuloCalendario.generarCalendarioGrupos(grupos, sedes, arbitros);
        actualizarPanelEstado();

        if (calendario != null) {
            salida.setText("Calendario generado correctamente.\n"
                    + "Partidos programados: " + calendario.length + "\n\n"
                    + generarVistaPartidos());
        }
    }

    /**
     * Simula todos los partidos de grupos.
     */
    private void simularGrupos() {
        if (!ModuloEstadisticasFinales.validarSimulacionPermitida()) {
            return;
        }

        ModuloCalendario.simularFaseCompleta(calendario);
        actualizarPanelEstado();
        mostrarTablaEnPantalla();
    }

    /**
     * Crea las llaves de eliminacion directa.
     */
    private void crearLlaves() {
        if (!ModuloEstadisticasFinales.validarSimulacionPermitida()) {
            return;
        }

        if (grupos == null) {
            mostrarAviso("Primero debes crear los grupos.");
            return;
        }

        Eliminacion.crearLlaves(grupos, capacidad);
        actualizarPanelEstado();

        salida.setText("Llaves de eliminacion creadas.\n"
                + "Ya puedes simular la fase eliminatoria.\n");
    }

    /**
     * Simula toda la fase eliminatoria.
     */
    private void simularEliminatoria() {
        if (!ModuloEstadisticasFinales.validarSimulacionPermitida()) {
            return;
        }

        Eliminacion.simularFaseEliminatoriaCompleta();
        actualizarPanelEstado();

        salida.setText(generarResumenFinalTexto());
    }

    /**
     * Muestra el resumen final del torneo.
     */
    private void mostrarResumenFinal() {
        Partido finalTorneo = Eliminacion.obtenerFinal();

        if (finalTorneo == null || !finalTorneo.isJugado() || Eliminacion.campeon == null) {
            mostrarAviso("Primero debes finalizar la fase eliminatoria.");
            return;
        }

        salida.setText(generarResumenFinalTexto());
        actualizarPanelEstado();
    }

    /**
     * Muestra los grupos en el area de texto.
     */
    private void mostrarGruposEnPantalla() {
        if (grupos == null) {
            mostrarAviso("Primero debes crear los grupos.");
            return;
        }

        String texto = "===== FASE DE GRUPOS =====\n\n";

        for (int i = 0; i < grupos.length; i++) {
            texto += grupos[i].getNombreGrupo() + "\n";
            texto += "------------------------------\n";

            Equipo[] equiposGrupo = grupos[i].getEquipos();

            for (int j = 0; j < equiposGrupo.length; j++) {
                texto += (j + 1) + ". " + equiposGrupo[j].getNombre() + "\n";
            }

            texto += "\n";
        }

        salida.setText(texto);
    }

    /**
     * Muestra la tabla de posiciones.
     */
    private void mostrarTablaEnPantalla() {
        if (grupos == null) {
            mostrarAviso("Primero debes crear los grupos.");
            return;
        }

        String texto = "===== TABLAS DE POSICIONES =====\n\n";

        for (int i = 0; i < grupos.length; i++) {
            Equipo[] copia = copiarEquipos(grupos[i].getEquipos());
            Eliminacion.ordenarEquipos(copia);

            texto += grupos[i].getNombreGrupo() + "\n";
            texto += "Pos  Equipo                 GF  GC  DG  Pts\n";
            texto += "--------------------------------------------\n";

            for (int j = 0; j < copia.length; j++) {
                int diferencia = copia[j].getGolesFavor() - copia[j].getGolesContra();

                texto += ajustarTexto(String.valueOf(j + 1), 5)
                        + ajustarTexto(copia[j].getNombre(), 23)
                        + ajustarTexto(String.valueOf(copia[j].getGolesFavor()), 4)
                        + ajustarTexto(String.valueOf(copia[j].getGolesContra()), 4)
                        + ajustarTexto(String.valueOf(diferencia), 4)
                        + ajustarTexto(String.valueOf(copia[j].getPuntos()), 5)
                        + "\n";
            }

            texto += "\n";
        }

        salida.setText(texto);
    }

    /**
     * Genera el texto con los partidos del calendario.
     */
    private String generarVistaPartidos() {
        String texto = "===== PARTIDOS DE GRUPOS =====\n\n";

        if (calendario == null) {
            return "No hay calendario generado.\n";
        }

        for (int i = 0; i < calendario.length; i++) {
            texto += (i + 1)
                    + ". "
                    + calendario[i].getEquipoLocal().getNombre()
                    + " vs "
                    + calendario[i].getEquipoVisitante().getNombre()
                    + "\n";
        }

        return texto;
    }

    /**
     * Genera el resumen final en texto.
     */
    private String generarResumenFinalTexto() {
        String texto = "===== RESUMEN FINAL DEL TORNEO =====\n\n";

        if (Eliminacion.campeon != null) {
            texto += "CAMPEON DEL MUNDO: " + Eliminacion.campeon.getNombre() + "\n";
        } else {
            texto += "CAMPEON DEL MUNDO: Pendiente\n";
        }

        Partido finalTorneo = Eliminacion.obtenerFinal();

        if (finalTorneo != null && finalTorneo.getGanador() != null) {
            Equipo subcampeon = obtenerSubcampeon(finalTorneo);

            if (subcampeon != null) {
                texto += "SUBCAMPEON: " + subcampeon.getNombre() + "\n";
            }
        }

        texto += "\n" + generarTopGoleadores();
        texto += "\n" + generarReporteDisciplinario();
        texto += "\n" + generarResumenFinanciero();

        return texto;
    }

    /**
     * Obtiene el equipo perdedor de la final.
     */
    private Equipo obtenerSubcampeon(Partido finalTorneo) {
        if (finalTorneo.getGanador() == finalTorneo.getEquipoLocal()) {
            return finalTorneo.getEquipoVisitante();
        }

        if (finalTorneo.getGanador() == finalTorneo.getEquipoVisitante()) {
            return finalTorneo.getEquipoLocal();
        }

        return null;
    }

    /**
     * Genera el Top 5 de goleadores.
     */
    private String generarTopGoleadores() {
        Jugador[] mejores = new Jugador[5];
        String[] paises = new String[5];

        for (int i = 0; i < equipos.length; i++) {
            if (equipos[i] != null && equipos[i].getJugadores() != null) {
                Jugador[] jugadores = equipos[i].getJugadores();

                for (int j = 0; j < jugadores.length; j++) {
                    insertarGoleador(mejores, paises, jugadores[j], equipos[i].getNombre());
                }
            }
        }

        String texto = "===== TOP 5 GOLEADORES =====\n";

        for (int i = 0; i < mejores.length; i++) {
            if (mejores[i] != null) {
                texto += (i + 1)
                        + ". "
                        + ajustarTexto(mejores[i].getNombre(), 30)
                        + " Pais: "
                        + ajustarTexto(paises[i], 12)
                        + " Goles: "
                        + mejores[i].getGoles()
                        + "\n";
            }
        }

        return texto;
    }

    /**
     * Inserta un jugador en el Top 5 de goleadores.
     */
    private void insertarGoleador(Jugador[] mejores, String[] paises, Jugador jugador, String pais) {
        if (jugador == null) {
            return;
        }

        for (int i = 0; i < mejores.length; i++) {
            if (mejores[i] == null || jugador.getGoles() > mejores[i].getGoles()) {
                for (int j = mejores.length - 1; j > i; j--) {
                    mejores[j] = mejores[j - 1];
                    paises[j] = paises[j - 1];
                }

                mejores[i] = jugador;
                paises[i] = pais;
                return;
            }
        }
    }

    /**
     * Genera el reporte disciplinario.
     */
    private String generarReporteDisciplinario() {
        String texto = "===== REPORTE DISCIPLINARIO =====\n";
        int encontrados = 0;

        for (int i = 0; i < equipos.length; i++) {
            if (equipos[i] != null && equipos[i].getJugadores() != null) {
                Jugador[] jugadores = equipos[i].getJugadores();

                for (int j = 0; j < jugadores.length; j++) {
                    int amarillas = jugadores[j].getTarjetasAmarillas();
                    int rojas = jugadores[j].getTarjetasRojas();

                    if (amarillas > 0 || rojas > 0) {
                        texto += jugadores[j].getNombre()
                                + " | Pais: "
                                + equipos[i].getNombre()
                                + " | Amarillas: "
                                + amarillas
                                + " | Rojas: "
                                + rojas
                                + " | Total: "
                                + (amarillas + rojas)
                                + "\n";

                        encontrados++;
                    }
                }
            }
        }

        if (encontrados == 0) {
            texto += "No hay incidencias registradas.\n";
        }

        return texto;
    }

    /**
     * Genera el resumen financiero y de asistencia.
     */
    private String generarResumenFinanciero() {
        Partido[] eliminacion = Eliminacion.obtenerPartidosEliminacion();

        int partidos = contarPartidosJugados(calendario) + contarPartidosJugados(eliminacion);
        int asistencia = sumarAsistencia(calendario) + sumarAsistencia(eliminacion);
        double recaudacion = sumarRecaudacion(calendario) + sumarRecaudacion(eliminacion);

        int promedio = 0;

        if (partidos > 0) {
            promedio = asistencia / partidos;
        }

        return "===== FINANZAS Y ASISTENCIA =====\n"
                + "Partidos jugados: " + partidos + "\n"
                + "Asistencia total: " + asistencia + "\n"
                + "Promedio por partido: " + promedio + "\n"
                + "Recaudacion total: $" + String.format("%.2f", recaudacion) + "\n";
    }

    /**
     * Cuenta partidos jugados.
     */
    private int contarPartidosJugados(Partido[] partidos) {
        int total = 0;

        if (partidos == null) {
            return total;
        }

        for (int i = 0; i < partidos.length; i++) {
            if (partidos[i] != null && partidos[i].isJugado()) {
                total++;
            }
        }

        return total;
    }

    /**
     * Suma asistencia.
     */
    private int sumarAsistencia(Partido[] partidos) {
        int total = 0;

        if (partidos == null) {
            return total;
        }

        for (int i = 0; i < partidos.length; i++) {
            if (partidos[i] != null && partidos[i].isJugado()) {
                total += partidos[i].getAsistencia();
            }
        }

        return total;
    }

    /**
     * Suma recaudacion.
     */
    private double sumarRecaudacion(Partido[] partidos) {
        double total = 0;

        if (partidos == null) {
            return total;
        }

        for (int i = 0; i < partidos.length; i++) {
            if (partidos[i] != null && partidos[i].isJugado()) {
                total += partidos[i].getRecaudacion();
            }
        }

        return total;
    }

    /**
     * Actualiza las tarjetas superiores.
     */
    private void actualizarPanelEstado() {
        equiposValor.setText(String.valueOf(contarEquipos()));

        if (grupos == null) {
            gruposValor.setText("0");
        } else {
            gruposValor.setText(String.valueOf(grupos.length));
        }

        int partidosJugados = contarPartidosJugados(calendario)
                + contarPartidosJugados(Eliminacion.obtenerPartidosEliminacion());

        partidosValor.setText(String.valueOf(partidosJugados));

        if (Eliminacion.campeon == null) {
            campeonValor.setText("Pendiente");
        } else {
            campeonValor.setText(Eliminacion.campeon.getNombre());
        }
    }

    /**
     * Cuenta equipos registrados.
     */
    private int contarEquipos() {
        int total = 0;

        for (int i = 0; i < equipos.length; i++) {
            if (equipos[i] != null) {
                total++;
            }
        }

        return total;
    }

    /**
     * Cuenta arbitros registrados.
     */
    private int contarArbitros() {
        int total = 0;

        for (int i = 0; i < arbitros.length; i++) {
            if (arbitros[i] != null) {
                total++;
            }
        }

        return total;
    }

    /**
     * Cuenta sedes registradas.
     */
    private int contarSedes() {
        int total = 0;

        for (int i = 0; i < sedes.length; i++) {
            if (sedes[i] != null) {
                total++;
            }
        }

        return total;
    }

    /**
     * Copia equipos para ordenarlos sin modificar el arreglo original.
     */
    private Equipo[] copiarEquipos(Equipo[] origen) {
        Equipo[] copia = new Equipo[origen.length];

        for (int i = 0; i < origen.length; i++) {
            copia[i] = origen[i];
        }

        return copia;
    }

    /**
     * Ajusta texto para que las tablas se vean alineadas.
     */
    private String ajustarTexto(String texto, int largo) {
        if (texto == null) {
            texto = "";
        }

        if (texto.length() > largo) {
            return texto.substring(0, largo - 1) + " ";
        }

        while (texto.length() < largo) {
            texto += " ";
        }

        return texto;
    }

    /**
     * Muestra un mensaje emergente.
     */
    private void mostrarAviso(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje);
    }
}
