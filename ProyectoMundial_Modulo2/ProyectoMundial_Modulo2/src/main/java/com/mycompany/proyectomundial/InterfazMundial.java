package com.mycompany.proyectomundial;

// Importaciones para construir la interfaz grafica con Java Swing y AWT.
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
 * Ventana principal del sistema.
 * Permite utilizar las opciones del proyecto desde una interfaz Swing.
 */
public class InterfazMundial extends JFrame {

    // Arreglos principales donde se guarda la informacion del torneo.
    private Equipo[] equipos;
    private CuerpoArbitral[] arbitros;
    private Sede[] sedes;
    private Grupo[] grupos;
    private Partido[] calendario;

    // Cantidad inicial de equipos. Se puede cambiar desde el combo.
    private int capacidad = 32;

    // Controla si ya se crearon las llaves de eliminacion.
    private boolean llavesCreadas = false;

    // Etiquetas que muestran datos rapidos en la parte superior.
    private JLabel equiposValor;
    private JLabel gruposValor;
    private JLabel partidosValor;
    private JLabel campeonValor;

    // Area central donde se muestran reportes, tablas y resultados.
    private JTextArea salida;

    // Selector para elegir 24, 32, 48 o 64 equipos.
    private JComboBox<String> selectorCapacidad;

    /**
     * Constructor de la ventana.
     * Aqui se prepara todo lo necesario para mostrar la interfaz.
     */
    public InterfazMundial() {
        configurarVentana();
        inicializarDatos();
        construirInterfaz();
        actualizarPanelEstado();
    }

    /**
     * Metodo principal para ejecutar esta ventana.
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
     * Configura las propiedades basicas de la ventana.
     */
    private void configurarVentana() {
        setTitle("Proyecto Mundial");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1180, 740);
        setMinimumSize(new Dimension(1040, 660));
        setLocationRelativeTo(null);
    }

    /**
     * Inicializa los arreglos y reinicia el estado del torneo.
     */
    private void inicializarDatos() {
        equipos = new Equipo[capacidad];
        arbitros = new CuerpoArbitral[30];
        sedes = new Sede[6];
        grupos = null;
        calendario = null;
        llavesCreadas = false;
        Eliminacion.campeon = null;
    }

    /**
     * Crea la estructura principal de la pantalla.
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
     * Crea la franja superior de la ventana.
     */
    private JPanel crearEncabezado() {
        JPanel panel = new JPanel(new BorderLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);

                // Se usa Graphics2D para dibujar con mejor calidad.
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                // Fondo principal del encabezado.
                g2.setColor(new Color(12, 35, 64));
                g2.fillRect(0, 0, getWidth(), getHeight());

                // Figuras decorativas.
                g2.setColor(new Color(16, 95, 111));
                g2.fillOval(getWidth() - 260, -90, 360, 230);

                g2.setColor(new Color(198, 161, 91));
                g2.fillOval(getWidth() - 120, 55, 170, 170);
            }
        };

        panel.setPreferredSize(new Dimension(0, 118));
        panel.setBorder(new EmptyBorder(22, 28, 18, 28));

        JLabel titulo = new JLabel("Proyecto Mundial");
        titulo.setForeground(Color.WHITE);
        titulo.setFont(new Font("SansSerif", Font.BOLD, 34));

        JLabel subtitulo = new JLabel("Gestion de equipos, grupos, calendario, eliminatorias y resumen final");
        subtitulo.setForeground(new Color(218, 231, 238));
        subtitulo.setFont(new Font("SansSerif", Font.PLAIN, 15));

        JPanel textos = new JPanel(new GridLayout(2, 1));
        textos.setOpaque(false);
        textos.add(titulo);
        textos.add(subtitulo);

        JLabel modulo = new JLabel("PANEL DE CONTROL");
        modulo.setHorizontalAlignment(SwingConstants.RIGHT);
        modulo.setForeground(new Color(247, 224, 155));
        modulo.setFont(new Font("SansSerif", Font.BOLD, 18));

        panel.add(textos, BorderLayout.WEST);
        panel.add(modulo, BorderLayout.EAST);

        return panel;
    }

    /**
     * Crea el menu lateral con todas las opciones del proyecto.
     */
    private JPanel crearMenuLateral() {
        JPanel menu = new JPanel();
        menu.setLayout(new GridLayout(0, 1, 0, 8));
        menu.setBackground(new Color(19, 42, 69));
        menu.setBorder(new EmptyBorder(18, 18, 18, 18));

        // Selector de cantidad de equipos.
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

        // Opciones del menu original: equipos, arbitros, sedes y demo.
        menu.add(crearEtiquetaMenu("Registro"));
        menu.add(crearBoton("1. Nuevo equipo", new Runnable() {
            @Override
            public void run() {
                agregarEquipoManual();
            }
        }));
        menu.add(crearBoton("2. Ver equipos", new Runnable() {
            @Override
            public void run() {
                verEquipos();
            }
        }));
        menu.add(crearBoton("3. Actualizar equipo", new Runnable() {
            @Override
            public void run() {
                actualizarEquipo();
            }
        }));
        menu.add(crearBoton("4. Nuevo arbitro", new Runnable() {
            @Override
            public void run() {
                agregarArbitroManual();
            }
        }));
        menu.add(crearBoton("5. Ver arbitros", new Runnable() {
            @Override
            public void run() {
                verArbitros();
            }
        }));
        menu.add(crearBoton("6. Actualizar arbitro", new Runnable() {
            @Override
            public void run() {
                actualizarArbitro();
            }
        }));
        menu.add(crearBoton("7. Nueva sede", new Runnable() {
            @Override
            public void run() {
                agregarSedeManual();
            }
        }));
        menu.add(crearBoton("8. Ver sedes", new Runnable() {
            @Override
            public void run() {
                verSedes();
            }
        }));
        menu.add(crearBoton("9. Actualizar sede", new Runnable() {
            @Override
            public void run() {
                actualizarSede();
            }
        }));
        menu.add(crearBoton("10. Modo demo", new Runnable() {
            @Override
            public void run() {
                cargarDemo();
            }
        }));

        // Opciones del modulo 2.
        menu.add(crearEtiquetaMenu("Modulo 2"));
        menu.add(crearBoton("11. Crear grupos", new Runnable() {
            @Override
            public void run() {
                crearGrupos();
            }
        }));
        menu.add(crearBoton("12. Mostrar grupos", new Runnable() {
            @Override
            public void run() {
                mostrarGruposEnPantalla();
            }
        }));
        menu.add(crearBoton("13. Reiniciar tablas", new Runnable() {
            @Override
            public void run() {
                reiniciarTablas();
            }
        }));
        menu.add(crearBoton("14. Mostrar tablas", new Runnable() {
            @Override
            public void run() {
                mostrarTablaEnPantalla();
            }
        }));

        // Opciones del modulo 3.
        menu.add(crearEtiquetaMenu("Modulo 3"));
        menu.add(crearBoton("15. Generar calendario", new Runnable() {
            @Override
            public void run() {
                generarCalendario();
            }
        }));
        menu.add(crearBoton("16. Partidos por grupo", new Runnable() {
            @Override
            public void run() {
                mostrarPartidosPorGrupo();
            }
        }));
        menu.add(crearBoton("17. Simular siguiente", new Runnable() {
            @Override
            public void run() {
                simularSiguientePartidoGrupo();
            }
        }));
        menu.add(crearBoton("18. Simular fase completa", new Runnable() {
            @Override
            public void run() {
                simularGrupos();
            }
        }));

        // Opciones del modulo 4.
        menu.add(crearEtiquetaMenu("Modulo 4"));
        menu.add(crearBoton("19. Clasificados", new Runnable() {
            @Override
            public void run() {
                mostrarClasificados();
            }
        }));
        menu.add(crearBoton("20. Crear llaves", new Runnable() {
            @Override
            public void run() {
                crearLlaves();
            }
        }));
        menu.add(crearBoton("21. Sig. eliminatorio", new Runnable() {
            @Override
            public void run() {
                simularSiguienteEliminatorio();
            }
        }));
        menu.add(crearBoton("22. Simular eliminatoria", new Runnable() {
            @Override
            public void run() {
                simularEliminatoria();
            }
        }));
        menu.add(crearBoton("23. Mostrar bracket", new Runnable() {
            @Override
            public void run() {
                mostrarBracket();
            }
        }));

        // Opcion del modulo 6.
        menu.add(crearEtiquetaMenu("Modulo 6"));
        menu.add(crearBoton("24. Resumen final", new Runnable() {
            @Override
            public void run() {
                mostrarResumenFinal();
            }
        }));

        // Se agrega scroll porque hay muchas opciones.
        JScrollPane scrollMenu = new JScrollPane(menu);
        scrollMenu.setBorder(null);
        scrollMenu.getVerticalScrollBar().setUnitIncrement(14);

        JPanel contenedor = new JPanel(new BorderLayout());
        contenedor.setPreferredSize(new Dimension(300, 0));
        contenedor.add(scrollMenu, BorderLayout.CENTER);

        return contenedor;
    }

    /**
     * Crea un texto pequeno para separar secciones en el menu lateral.
     */
    private JLabel crearEtiquetaMenu(String texto) {
        JLabel etiqueta = new JLabel(texto.toUpperCase());
        etiqueta.setForeground(new Color(174, 192, 207));
        etiqueta.setFont(new Font("SansSerif", Font.BOLD, 12));
        return etiqueta;
    }

    /**
     * Crea un boton con estilo y le asigna una accion.
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

        boton.addActionListener(e -> accion.run());

        return boton;
    }

    /**
     * Crea el panel central donde se muestran las tarjetas y el area de texto.
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

        salida.setText("Bienvenido al sistema del Mundial.\n\n"
                + "Puedes registrar datos manualmente o usar el modo demo.\n\n"
                + "Flujo recomendado:\n"
                + "1. Registrar equipos, arbitros y sedes, o cargar modo demo\n"
                + "2. Crear grupos\n"
                + "3. Generar calendario\n"
                + "4. Simular partidos de grupo\n"
                + "5. Crear llaves de eliminacion\n"
                + "6. Simular fase eliminatoria\n"
                + "7. Mostrar resumen final\n");

        JScrollPane scroll = new JScrollPane(salida);
        scroll.setBorder(BorderFactory.createLineBorder(new Color(218, 226, 235)));

        contenido.add(scroll, BorderLayout.CENTER);
        contenido.add(crearPie(), BorderLayout.SOUTH);

        return contenido;
    }

    /**
     * Crea las tarjetas superiores de resumen.
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
     * Crea el valor grande que aparece dentro de cada tarjeta.
     */
    private JLabel crearValorTarjeta() {
        JLabel valor = new JLabel("-");
        valor.setForeground(new Color(20, 30, 44));
        valor.setFont(new Font("SansSerif", Font.BOLD, 24));
        return valor;
    }

    /**
     * Crea una tarjeta visual con titulo, valor y color lateral.
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
     * Crea el pie inferior de la ventana.
     */
    private JPanel crearPie() {
        JPanel pie = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        pie.setOpaque(false);

        JLabel texto = new JLabel("Proyecto Mundial");
        texto.setForeground(new Color(106, 118, 132));
        texto.setFont(new Font("SansSerif", Font.PLAIN, 12));

        pie.add(texto);

        return pie;
    }

    /**
     * Reinicia el torneo con la cantidad seleccionada.
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

        salida.setText("Nuevo torneo creado.\n\n"
                + "Capacidad: " + capacidad + " equipos\n"
                + "Sedes disponibles para registrar: " + sedes.length + "\n"
                + "Arbitros disponibles para registrar: " + arbitros.length + "\n");
    }

    /**
     * Agrega un equipo usando el metodo existente en la clase Equipo.
     */
    private void agregarEquipoManual() {
        Equipo.agregarEquipo(equipos);
        actualizarPanelEstado();
        salida.setText(generarTextoEquipos());
    }

    /**
     * Muestra los equipos en el area central.
     */
    private void verEquipos() {
        salida.setText(generarTextoEquipos());
    }

    /**
     * Actualiza un equipo usando el metodo existente en Equipo.
     */
    private void actualizarEquipo() {
        Equipo.actualizarEquipos(equipos);
        actualizarPanelEstado();
        salida.setText(generarTextoEquipos());
    }

    /**
     * Agrega un arbitro usando el metodo existente.
     */
    private void agregarArbitroManual() {
        CuerpoArbitral.agregarArbitro(arbitros);
        actualizarPanelEstado();
        salida.setText(generarTextoArbitros());
    }

    /**
     * Muestra los arbitros registrados.
     */
    private void verArbitros() {
        salida.setText(generarTextoArbitros());
    }

    /**
     * Actualiza un arbitro.
     */
    private void actualizarArbitro() {
        CuerpoArbitral.actualizarArbitros(arbitros);
        actualizarPanelEstado();
        salida.setText(generarTextoArbitros());
    }

    /**
     * Agrega una sede usando el metodo existente.
     */
    private void agregarSedeManual() {
        Sede.agregarSede(sedes);
        actualizarPanelEstado();
        salida.setText(generarTextoSedes());
    }

    /**
     * Muestra las sedes registradas.
     */
    private void verSedes() {
        salida.setText(generarTextoSedes());
    }

    /**
     * Actualiza una sede.
     */
    private void actualizarSede() {
        Sede.actualizarSedes(sedes);
        actualizarPanelEstado();
        salida.setText(generarTextoSedes());
    }

    /**
     * Llena automaticamente equipos, arbitros y sedes para pruebas.
     */
    private void cargarDemo() {
        Equipo.modoDemoEquipos(equipos);
        CuerpoArbitral.modoDemoArbitros(arbitros);
        Sede.modoDemoSedes(sedes);

        actualizarPanelEstado();

        salida.setText("Modo demo cargado correctamente.\n\n"
                + "Equipos registrados: " + contarEquipos() + " de " + equipos.length + "\n"
                + "Arbitros registrados: " + contarArbitros() + " de " + arbitros.length + "\n"
                + "Sedes registradas: " + contarSedes() + " de " + sedes.length + "\n");
    }

    /**
     * Crea los grupos con los equipos registrados.
     */
    private void crearGrupos() {
        grupos = ModuloGrupos.crearGrupos(equipos);
        actualizarPanelEstado();

        if (grupos != null) {
            mostrarGruposEnPantalla();
        }
    }

    /**
     * Muestra los grupos creados.
     */
    private void mostrarGruposEnPantalla() {
        if (grupos == null) {
            mostrarAviso("Primero debe crear los grupos.");
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
     * Reinicia las estadisticas de la tabla de grupos.
     */
    private void reiniciarTablas() {
        if (grupos == null) {
            mostrarAviso("Primero debe crear los grupos.");
            return;
        }

        ModuloGrupos.iniciarTablaGrupo(grupos);
        actualizarPanelEstado();

        salida.setText("Tablas de grupos reiniciadas correctamente.\n");
    }

    /**
     * Muestra la tabla de posiciones de cada grupo.
     */
    private void mostrarTablaEnPantalla() {
        if (grupos == null) {
            mostrarAviso("Primero debe crear los grupos.");
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
     * Genera el calendario de partidos de grupos.
     */
    private void generarCalendario() {
        calendario = ModuloCalendario.generarCalendarioGrupos(grupos, sedes, arbitros);
        actualizarPanelEstado();

        if (calendario != null) {
            salida.setText(generarVistaPartidos());
        }
    }

    /**
     * Muestra los partidos agrupados por grupo.
     */
    private void mostrarPartidosPorGrupo() {
        if (calendario == null || grupos == null) {
            mostrarAviso("Primero debe generar el calendario.");
            return;
        }

        salida.setText(generarVistaPartidosPorGrupo());
    }

    /**
     * Simula solamente el siguiente partido pendiente de grupos.
     */
    private void simularSiguientePartidoGrupo() {
        if (!ModuloEstadisticasFinales.validarSimulacionPermitida()) {
            return;
        }

        if (calendario == null) {
            mostrarAviso("Primero debe generar el calendario.");
            return;
        }

        Partido siguiente = ModuloCalendario.obtenerSiguientePartido(calendario);

        if (siguiente != null) {
            ModuloCalendario.simularPartidoAPartido(siguiente, true);
        }

        actualizarPanelEstado();
        salida.setText(generarVistaPartidosPorGrupo());
    }

    /**
     * Simula todos los partidos pendientes de la fase de grupos.
     */
    private void simularGrupos() {
        if (!ModuloEstadisticasFinales.validarSimulacionPermitida()) {
            return;
        }

        if (calendario == null) {
            mostrarAviso("Primero debe generar el calendario.");
            return;
        }

        ModuloCalendario.simularFaseCompleta(calendario);
        actualizarPanelEstado();
        mostrarTablaEnPantalla();
    }

    /**
     * Muestra los equipos clasificados a eliminacion.
     */
    private void mostrarClasificados() {
        if (grupos == null) {
            mostrarAviso("Primero debe crear los grupos.");
            return;
        }

        Equipo[] clasificados = Eliminacion.obtenerClasificados(grupos, capacidad);

        String texto = "===== EQUIPOS CLASIFICADOS =====\n\n";

        for (int i = 0; i < clasificados.length; i++) {
            texto += (i + 1) + ". " + clasificados[i].getNombre() + "\n";
        }

        salida.setText(texto);
    }

    /**
     * Crea las llaves de eliminacion directa.
     */
    private void crearLlaves() {
        if (!ModuloEstadisticasFinales.validarSimulacionPermitida()) {
            return;
        }

        if (grupos == null) {
            mostrarAviso("Primero debe crear los grupos.");
            return;
        }

        Eliminacion.crearLlaves(grupos, capacidad);
        llavesCreadas = true;
        actualizarPanelEstado();

        salida.setText(generarBracketTexto());
    }

    /**
     * Simula el siguiente partido eliminatorio pendiente.
     */
    private void simularSiguienteEliminatorio() {
        if (!ModuloEstadisticasFinales.validarSimulacionPermitida()) {
            return;
        }

        if (!llavesCreadas) {
            mostrarAviso("Primero debe crear las llaves de eliminacion.");
            return;
        }

        Eliminacion.simularSiguientePartido();

        actualizarPanelEstado();
        salida.setText(generarBracketTexto());
    }

    /**
     * Simula toda la fase eliminatoria.
     */
    private void simularEliminatoria() {
        if (!ModuloEstadisticasFinales.validarSimulacionPermitida()) {
            return;
        }

        if (!llavesCreadas) {
            mostrarAviso("Primero debe crear las llaves de eliminacion.");
            return;
        }

        Eliminacion.simularFaseEliminatoriaCompleta();

        actualizarPanelEstado();
        salida.setText(generarBracketTexto());
    }

    /**
     * Muestra el bracket de eliminacion.
     */
    private void mostrarBracket() {
        if (!llavesCreadas) {
            mostrarAviso("Primero debe crear las llaves de eliminacion.");
            return;
        }

        salida.setText(generarBracketTexto());
    }

    /**
     * Muestra el resumen final del torneo.
     */
    private void mostrarResumenFinal() {
        Partido finalTorneo = Eliminacion.obtenerFinal();

        if (finalTorneo == null || !finalTorneo.isJugado() || Eliminacion.campeon == null) {
            mostrarAviso("Primero debe finalizar la fase eliminatoria.");
            return;
        }

        
        salida.setText(generarResumenFinalTexto());
        actualizarPanelEstado();
    }

    /**
     * Genera el texto para mostrar equipos.
     */
    private String generarTextoEquipos() {
        String texto = "===== EQUIPOS REGISTRADOS =====\n\n";

        for (int i = 0; i < equipos.length; i++) {
            if (equipos[i] != null) {
                texto += "Equipo #" + (i + 1) + "\n";
                texto += "Pais: " + equipos[i].getNombre() + "\n";
                texto += "Director tecnico: " + equipos[i].getDt() + "\n";
                texto += "Goles a favor: " + equipos[i].getGolesFavor() + "\n";
                texto += "Goles en contra: " + equipos[i].getGolesContra() + "\n";
                texto += "Puntos: " + equipos[i].getPuntos() + "\n";
                texto += "Jugadores:\n";

                Jugador[] jugadores = equipos[i].getJugadores();

                for (int j = 0; jugadores != null && j < jugadores.length; j++) {
                    texto += "  " + (j + 1) + ". " + jugadores[j].getNombre()
                            + " | Goles: " + jugadores[j].getGoles()
                            + " | Amarillas: " + jugadores[j].getTarjetasAmarillas()
                            + " | Rojas: " + jugadores[j].getTarjetasRojas()
                            + "\n";
                }

                texto += "\n";
            }
        }

        if (contarEquipos() == 0) {
            texto += "No hay equipos registrados.\n";
        }

        return texto;
    }

    /**
     * Genera el texto para mostrar arbitros.
     */
    private String generarTextoArbitros() {
        String texto = "===== ARBITROS REGISTRADOS =====\n\n";

        for (int i = 0; i < arbitros.length; i++) {
            if (arbitros[i] != null) {
                texto += "Arbitro #" + (i + 1) + "\n";
                texto += "Nombre: " + arbitros[i].getNombre() + "\n";
                texto += "Nacionalidad: " + arbitros[i].getNacionalidad() + "\n";
                texto += "Partidos dirigidos: " + arbitros[i].getPartidosDirigidos() + "\n";
                texto += "Tarjetas mostradas: " + arbitros[i].getTarjetasMostradas() + "\n\n";
            }
        }

        if (contarArbitros() == 0) {
            texto += "No hay arbitros registrados.\n";
        }

        return texto;
    }

    /**
     * Genera el texto para mostrar sedes.
     */
    private String generarTextoSedes() {
        String texto = "===== SEDES REGISTRADAS =====\n\n";

        for (int i = 0; i < sedes.length; i++) {
            if (sedes[i] != null) {
                texto += "Sede #" + (i + 1) + "\n";
                texto += "Nombre: " + sedes[i].getNombre() + "\n";
                texto += "Ciudad: " + sedes[i].getCiudad() + "\n";
                texto += "Capacidad: " + sedes[i].getCapacidad() + "\n";
                texto += "Precio entrada: " + sedes[i].getPrecioEntrada() + "\n\n";
            }
        }

        if (contarSedes() == 0) {
            texto += "No hay sedes registradas.\n";
        }

        return texto;
    }

    /**
     * Genera el texto del calendario general.
     */
    private String generarVistaPartidos() {
        String texto = "===== CALENDARIO DE GRUPOS =====\n\n";

        if (calendario == null) {
            return "No hay calendario generado.\n";
        }

        for (int i = 0; i < calendario.length; i++) {
            texto += (i + 1) + ". "
                    + calendario[i].getEquipoLocal().getNombre()
                    + " "
                    + calendario[i].getGolesLocal()
                    + " - "
                    + calendario[i].getGolesVisitante()
                    + " "
                    + calendario[i].getEquipoVisitante().getNombre();

            if (calendario[i].isJugado()) {
                texto += " | Jugado";
            } else {
                texto += " | Pendiente";
            }

            texto += "\n";
        }

        return texto;
    }

    /**
     * Genera el texto de partidos separados por grupo.
     */
    private String generarVistaPartidosPorGrupo() {
        String texto = "===== PARTIDOS POR GRUPO =====\n\n";

        if (calendario == null || grupos == null) {
            return "No hay partidos generados.\n";
        }

        int posicion = 0;

        for (int i = 0; i < grupos.length; i++) {
            texto += grupos[i].getNombreGrupo() + "\n";
            texto += "------------------------------\n";

            for (int j = 0; j < 6 && posicion < calendario.length; j++) {
                Partido partido = calendario[posicion];

                texto += (j + 1) + ". "
                        + partido.getEquipoLocal().getNombre()
                        + " "
                        + partido.getGolesLocal()
                        + " - "
                        + partido.getGolesVisitante()
                        + " "
                        + partido.getEquipoVisitante().getNombre();

                if (partido.isJugado()) {
                    texto += " | Jugado";
                } else {
                    texto += " | Pendiente";
                }

                texto += "\n";
                posicion++;
            }

            texto += "\n";
        }

        return texto;
    }

    /**
     * Genera el texto del bracket eliminatorio.
     */
    private String generarBracketTexto() {
        if (!llavesCreadas) {
            return "Primero debe crear las llaves de eliminacion.\n";
        }

        Partido[] partidos = Eliminacion.obtenerPartidosEliminacion();

        if (partidos == null || partidos.length == 0) {
            return "No hay partidos eliminatorios generados.\n";
        }

        String texto = "===== BRACKET DEL MUNDIAL =====\n\n";

        for (int i = 0; i < partidos.length; i++) {
            if (partidos[i] != null) {
                texto += partidos[i].getFase() + " - "
                        + partidos[i].getEquipoLocal().getNombre()
                        + " "
                        + partidos[i].getGolesLocal()
                        + " - "
                        + partidos[i].getGolesVisitante()
                        + " "
                        + partidos[i].getEquipoVisitante().getNombre();

                if (partidos[i].isJugado() && partidos[i].getGanador() != null) {
                    texto += " | Ganador: " + partidos[i].getGanador().getNombre();
                } else {
                    texto += " | Pendiente";
                }

                texto += "\n";
            }
        }

        if (Eliminacion.campeon != null) {
            texto += "\nCAMPEON DEL MUNDO: " + Eliminacion.campeon.getNombre() + "\n";
        }

        return texto;
    }

    /**
     * Genera el resumen final del torneo.
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
     * Obtiene el equipo que perdio la final.
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
                        + ajustarTexto(paises[i], 14)
                        + " Goles: "
                        + mejores[i].getGoles()
                        + "\n";
            }
        }

        if (mejores[0] == null) {
            texto += "No hay goles registrados.\n";
        }

        return texto;
    }

    /**
     * Inserta un jugador dentro del Top 5 si tiene mas goles.
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
     * Genera el reporte de tarjetas.
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
     * Genera el resumen de asistencia y recaudacion.
     */
    private String generarResumenFinanciero() {
        Partido[] eliminacion = null;

        if (llavesCreadas) {
            eliminacion = Eliminacion.obtenerPartidosEliminacion();
        }

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
     * Actualiza las tarjetas superiores.
     */
    private void actualizarPanelEstado() {
        equiposValor.setText(String.valueOf(contarEquipos()));

        if (grupos == null) {
            gruposValor.setText("0");
        } else {
            gruposValor.setText(String.valueOf(grupos.length));
        }

        int partidosJugados = contarPartidosJugados(calendario);

        if (llavesCreadas) {
            partidosJugados += contarPartidosJugados(Eliminacion.obtenerPartidosEliminacion());
        }

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
     * Suma la asistencia de partidos jugados.
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
     * Suma la recaudacion de partidos jugados.
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
     * Copia un arreglo de equipos para ordenarlo sin modificar el original.
     */
    private Equipo[] copiarEquipos(Equipo[] origen) {
        Equipo[] copia = new Equipo[origen.length];

        for (int i = 0; i < origen.length; i++) {
            copia[i] = origen[i];
        }

        return copia;
    }

    /**
     * Ajusta texto para alinear columnas.
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
