import tkinter as tk
from tkinter import ttk
import os
import webbrowser

def open_pizarra_virtual():
    os.system('python PizarraGesto1.py')

def open_instructions(): 
    webbrowser.open("https://drive.google.com/file/d/1jAHighsCEpm39-Dm0TSZvkAlHAde8JZ9/view?usp=sharing")

def close_app():
    # Mostrar el mensaje "Saliendo..."
    exit_label = ttk.Label(root, text="Saliendo ...", font=("Helvetica", 16))
    exit_label.grid(row=5, column=0, columnspan=2, pady=10)

    # Programar el cierre de la aplicación después de 3 segundos
    root.after(3000, root.destroy)

root = tk.Tk()
root.title("GestoTab")

# Ajustar el tamaño de la ventana
content_width = 600
content_height = 500
screen_width = root.winfo_screenwidth()
screen_height = root.winfo_screenheight()
x = (screen_width // 2) - (content_width // 2)
y = (screen_height // 2) - (content_height // 2)
root.geometry('{}x{}+{}+{}'.format(content_width, content_height, x, y))

root.attributes('-alpha', 0.90)

root.configure(bg='#f0f0f0')

style = ttk.Style()
style.configure('TButton', font=('Helvetica', 12), padding=10, foreground='blue') 
style.map('TButton', foreground=[('active', 'green')])
style.configure('TLabel', font=('Helvetica', 14))

# Añadir imagen para la interfaz
empresa_image = tk.PhotoImage(file='./Imagenes/logoepisi.png')
desired_width = 100
desired_height = 100
empresa_image = empresa_image.subsample(int(empresa_image.width() / desired_width), int(empresa_image.height() / desired_height))


image_frame = ttk.Frame(root)
image_frame.grid(row=0, column=1, padx=20, pady=20, sticky='ne')

image_label = ttk.Label(image_frame, image=empresa_image)
image_label.pack(fill=tk.BOTH, expand=tk.YES)

# Título en el interfaz
title_label = ttk.Label(root, text="Pizarra\nGestoTab", font=('Bauhaus 93', 40))
title_label.grid(row=0, column=0, columnspan=2, padx=20, pady=10, sticky='n')

# Descripción 
description_frame = ttk.Frame(root, relief='solid', borderwidth=2)
description_frame.grid(row=1, column=0, columnspan=2, padx=20, pady=20)

description_label = ttk.Label(description_frame, text="GestoTab es un sistema de escritura a mano en el aire que permite a los usuarios interactuar de manera eficiente y precisa con dispositivos digitales mediante el seguimiento y detección de los movimientos de las yemas de los dedos",
                              wraplength=560, justify="center", font=("Helvetica", 12))
description_label.pack(padx=10, pady=10)

# Frame para los botones
button_frame = ttk.Frame(root)
button_frame.grid(row=2, column=0, columnspan=2, pady=10)

# Botones de acción centrados
open_button = ttk.Button(button_frame, text="Abrir Pizarra", command=open_pizarra_virtual)
open_button.grid(row=0, column=0, padx=10, pady=10)

exit_button = ttk.Button(button_frame, text="Salir", command=close_app)
exit_button.grid(row=0, column=1, padx=10, pady=10)

# Botón para instrucciones
instructions_button = ttk.Button(button_frame, text="Instrucciones de Uso", command=open_instructions, style='Small.TButton')
instructions_button.grid(row=1, column=0, columnspan=2, padx=10, pady=10)

# Estilo para el botón de instrucciones
small_button_style = ttk.Style()
small_button_style.configure('Small.TButton', font=('Helvetica', 10), padding=5)

# Enlaces a redes sociales del desarrollador centrados y con tamaño de letra más pequeño
social_label = ttk.Label(root, text="Síguenos en nuestras redes sociales:", font=("Helvetica", 10))
social_label.grid(row=3, column=0, columnspan=2, pady=1)

social_text = """
Facebook: @Jesusedward / Instagram: @jesusedward / TikTok: @Jesuseward
"""
social_links = ttk.Label(root, text=social_text, font=("Helvetica", 10), justify='center')
social_links.grid(row=4, column=0, columnspan=2, pady=5)

root.mainloop()
