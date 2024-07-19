import tkinter as tk
from tkinter import filedialog, ttk
import cv2
import numpy as np
import mediapipe as mp
from collections import deque
from PIL import Image, ImageTk

# Función para guardar el lienzo como imagen
def save_canvas():
    global paintWindow
    filename = filedialog.asksaveasfilename(defaultextension=".png", filetypes=[("PNG files", "*.png")])
    if filename:
        cv2.imwrite(filename, paintWindow)

# Función para limpiar el lienzo
def clear_canvas():
    global bpoints, gpoints, rpoints, ypoints
    global blue_index, green_index, red_index, yellow_index
    bpoints = [deque(maxlen=1024)]
    gpoints = [deque(maxlen=1024)]
    rpoints = [deque(maxlen=1024)]
    ypoints = [deque(maxlen=1024)]
    blue_index = 0
    green_index = 0
    red_index = 0
    yellow_index = 0
    paintWindow[67:, :, :] = 255

# Función para cerrar la aplicación
def close_app():
    root.destroy()

# Función para cambiar el grosor del puntero
def change_thickness(val):
    global brush_thickness
    brush_thickness = int(val)

# Función para cambiar la cámara
def change_camera(val):
    global cap
    cap.release()
    cap = cv2.VideoCapture(int(val))

# Inicialización de Tkinter
root = tk.Tk()
root.title("Paint con Cámara")
root.geometry("800x500")

# Canvas para mostrar la cámara
camera_canvas = tk.Canvas(root, width=640, height=480)
camera_canvas.grid(row=0, column=0, padx=10, pady=10)

# Marco para los botones
button_frame = tk.Frame(root, bg='#ddeeff')
button_frame.grid(row=0, column=1, padx=10, pady=10, sticky='n')

# Estilos de los botones ttk
style = ttk.Style()

style.configure('Save.TButton', foreground='#000000', background='#ddeeff', font=('Helvetica', 12))
style.configure('Clear.TButton', foreground='#000000', background='#ddeeff', font=('Helvetica', 12))
style.configure('Close.TButton', foreground='#000000', background='#ddeeff', font=('Helvetica', 12))


# Botones ttk
save_button = ttk.Button(button_frame, text="Guardar", command=save_canvas, style='Save.TButton')
save_button.pack(pady=5, padx=10, fill=tk.X)

clear_button = ttk.Button(button_frame, text="Limpiar", command=clear_canvas, style='Clear.TButton')
clear_button.pack(pady=5, padx=10, fill=tk.X)

close_button = ttk.Button(button_frame, text="Cerrar", command=close_app, style='Close.TButton')
close_button.pack(pady=5, padx=10, fill=tk.X)

# Control deslizante para cambiar el grosor del puntero
thickness_label = tk.Label(button_frame, text="Grosor del puntero:", bg='#ddeeff')
thickness_label.pack(pady=5)
thickness_slider = tk.Scale(button_frame, from_=1, to=20, orient=tk.HORIZONTAL, command=change_thickness)
thickness_slider.pack(pady=5, fill=tk.X)

# Selección de cámara
camera_label = tk.Label(button_frame, text="Seleccionar cámara:", bg='#ddeeff')
camera_label.pack(pady=10)

camera_var = tk.StringVar(button_frame)
camera_var.set("0")  # Valor por defecto de la cámara
camera_option_menu = tk.OptionMenu(button_frame, camera_var, "0", "1", "2", command=change_camera)
camera_option_menu.pack(pady=5, fill=tk.X)

# Descripción
description_label = tk.Label(button_frame, text=" GestoTab V.001", bg='#ddeeff')
description_label.pack(pady=10)

# Configuración de la cámara y MediaPipe
cap = cv2.VideoCapture(0)
mpHands = mp.solutions.hands
hands = mpHands.Hands(max_num_hands=1, min_detection_confidence=0.7)
mpDraw = mp.solutions.drawing_utils

# Listas de deques para almacenar los puntos de diferentes colores.
bpoints = [deque(maxlen=1024)]
gpoints = [deque(maxlen=1024)]
rpoints = [deque(maxlen=1024)]
ypoints = [deque(maxlen=1024)]

# Índices para marcar puntos en los arrays de colores específicos.
blue_index = 0
green_index = 0
red_index = 0
yellow_index = 0

# Kernel para operaciones de dilatación.
kernel = np.ones((5, 5), np.uint8)

# Colors: Lista de colores (azul, verde, rojo, amarillo). colorIndex: Índice para el color actual
colors = [(255, 0, 0), (0, 255, 0), (0, 0, 255), (0, 255, 255)]
colorIndex = 0
brush_thickness = 2  # Grosor inicial del puntero

# Configuración del Lienzo
paintWindow = np.zeros((471, 636, 3)) + 255
paintWindow = cv2.circle(paintWindow, (90, 33), 30, (0, 0, 0), 2)  # Clear
paintWindow = cv2.circle(paintWindow, (207, 33), 30, (255, 0, 0), 2)  # Blue
paintWindow = cv2.circle(paintWindow, (324, 33), 30, (0, 255, 0), 2)  # Green
paintWindow = cv2.circle(paintWindow, (441, 33), 30, (0, 0, 255), 2)  # Red
paintWindow = cv2.circle(paintWindow, (558, 33), 30, (0, 255, 255), 2)  # Yellow

cv2.putText(paintWindow, "LIMPIAR", (65, 40), cv2.FONT_HERSHEY_SIMPLEX, 0.5, (0, 0, 0), 2, cv2.LINE_AA)
cv2.putText(paintWindow, "AZUL", (185, 40), cv2.FONT_HERSHEY_SIMPLEX, 0.5, (0, 0, 0), 2, cv2.LINE_AA)
cv2.putText(paintWindow, "VERDE", (302, 40), cv2.FONT_HERSHEY_SIMPLEX, 0.5, (0, 0, 0), 2, cv2.LINE_AA)
cv2.putText(paintWindow, "ROJO", (420, 40), cv2.FONT_HERSHEY_SIMPLEX, 0.5, (0, 0, 0), 2, cv2.LINE_AA)
cv2.putText(paintWindow, "AMARILLO", (495, 40), cv2.FONT_HERSHEY_SIMPLEX, 0.5, (0, 0, 0), 2, cv2.LINE_AA)

def update_frame():
    global bpoints, gpoints, rpoints, ypoints
    global blue_index, green_index, red_index, yellow_index
    global paintWindow, colorIndex, brush_thickness

    ret, frame = cap.read()
    if not ret:
        return

    frame = cv2.flip(frame, 1)
    framergb = cv2.cvtColor(frame, cv2.COLOR_BGR2RGB)

    frame = cv2.circle(frame, (90, 33), 30, (0, 0, 0), 2)  # Limpiar
    frame = cv2.circle(frame, (207, 33), 30, (255, 0, 0), 2)  # Azul
    frame = cv2.circle(frame, (324, 33), 30, (0, 255, 0), 2)  # Verde
    frame = cv2.circle(frame, (441, 33), 30, (0, 0, 255), 2)  # Rojo
    frame = cv2.circle(frame, (558, 33), 30, (0, 255, 255), 2)  # Amarillo

    cv2.putText(frame, "LIMPIAR", (65, 40), cv2.FONT_HERSHEY_SIMPLEX, 0.5, (0, 0, 0), 2, cv2.LINE_AA)
    cv2.putText(frame, "AZUL", (185, 40), cv2.FONT_HERSHEY_SIMPLEX, 0.5, (0, 0, 0), 2, cv2.LINE_AA)
    cv2.putText(frame, "VERDE", (302, 40), cv2.FONT_HERSHEY_SIMPLEX, 0.5, (0, 0, 0), 2, cv2.LINE_AA)
    cv2.putText(frame, "ROJO", (420, 40), cv2.FONT_HERSHEY_SIMPLEX, 0.5, (0, 0, 0), 2, cv2.LINE_AA)
    cv2.putText(frame, "AMARILLO", (495, 40), cv2.FONT_HERSHEY_SIMPLEX, 0.5, (0, 0, 0), 2, cv2.LINE_AA)

    result = hands.process(framergb)

    if result.multi_hand_landmarks:
        landmarks = []
        for handslms in result.multi_hand_landmarks:
            for lm in handslms.landmark:
                lmx = int(lm.x * 640)
                lmy = int(lm.y * 480)
                landmarks.append([lmx, lmy])

            mpDraw.draw_landmarks(frame, handslms, mpHands.HAND_CONNECTIONS)
        fore_finger = (landmarks[8][0], landmarks[8][1])
        center = fore_finger
        thumb = (landmarks[4][0], landmarks[4][1])
        cv2.circle(frame, center, 3, (0, 255, 0), -1)
        if thumb[1] - center[1] < 30:
            bpoints.append(deque(maxlen=512))
            blue_index += 1
            gpoints.append(deque(maxlen=512))
            green_index += 1
            rpoints.append(deque(maxlen=512))
            red_index += 1
            ypoints.append(deque(maxlen=512))
            yellow_index += 1
        elif center[1] <= 65:
            if 60 <= center[0] <= 120:  # Clear Button
                bpoints = [deque(maxlen=512)]
                gpoints = [deque(maxlen=512)]
                rpoints = [deque(maxlen=512)]
                ypoints = [deque(maxlen=512)]
                blue_index = 0
                green_index = 0
                red_index = 0
                yellow_index = 0
                paintWindow[67:, :, :] = 255
            elif 177 <= center[0] <= 237:
                colorIndex = 0  # Azul
            elif 294 <= center[0] <= 354:
                colorIndex = 1  # Verde
            elif 411 <= center[0] <= 471:
                colorIndex = 2  # Rojo
            elif 528 <= center[0] <= 588:
                colorIndex = 3  # Amarillo
        else:
            if colorIndex == 0:
                bpoints[blue_index].appendleft(center)
            elif colorIndex == 1:
                gpoints[green_index].appendleft(center)
            elif colorIndex == 2:
                rpoints[red_index].appendleft(center)
            elif colorIndex == 3:
                ypoints[yellow_index].appendleft(center)
    else:
        bpoints.append(deque(maxlen=512))
        blue_index += 1
        gpoints.append(deque(maxlen=512))
        green_index += 1
        rpoints.append(deque(maxlen=512))
        red_index += 1
        ypoints.append(deque(maxlen=512))
        yellow_index += 1

    points = [bpoints, gpoints, rpoints, ypoints]
    for i in range(len(points)):
        for j in range(len(points[i])):
            for k in range(1, len(points[i][j])):
                if points[i][j][k - 1] is None or points[i][j][k] is None:
                    continue
                cv2.line(frame, points[i][j][k - 1], points[i][j][k], colors[i], brush_thickness)
                cv2.line(paintWindow, points[i][j][k - 1], points[i][j][k], colors[i], brush_thickness)

    img = Image.fromarray(cv2.cvtColor(frame, cv2.COLOR_BGR2RGB))
    imgtk = ImageTk.PhotoImage(image=img)
    camera_canvas.create_image(0, 0, anchor=tk.NW, image=imgtk)
    camera_canvas.imgtk = imgtk

    root.after(10, update_frame)

root.after(0, update_frame)
root.mainloop()

cap.release()
cv2.destroyAllWindows()
