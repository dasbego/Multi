__author__ = 'bego'

import random
import copy

def edoinit():
    jarras = []
    jarras.append([12, random.randint(0, 12)])
    jarras.append([8, random.randint(0, 8)])
    jarras.append([3, random.randint(0, 3)])
    return jarras


def fn_sucesores(jarras):
    sucesores = []
    #sucesores al tirar jarras
    for i in range(3):
        tmp = jarras[i][1]
        jarras[i][1] = 0
        sucesores.append(copy.deepcopy(jarras))
        jarras[i][1] = tmp

    #sucesores al llenar jarras
    for i in range(3):
        tmp = jarras[i][1]
        jarras[i][1] = jarras[i][0]
        sucesores.append(copy.deepcopy(jarras))
        jarras[i][1] = tmp

    #sucesores al pasar contenido a otro
    for i in range(3):
        for k in range(3):
            #si no es la misma jarra
            if i != k:
                #la jarra tiene contenido
                if jarras[i][1] > 0:
                    #temps para los que se van amodificar
                    tmp = jarras[i][1]
                    tmp2 = jarras[k][1]
                    #la jarra a la que se le pasara no esta llena
                    if jarras[k][1] < jarras[k][0]:
                        jarras[k][1] += jarras[i][1]
                        #si sobrepasa el contenido el sobrante lo devuelve a la jarra que paso
                        if jarras[k][1] > jarras[k][0]:
                            #calcula el sobrante
                            rest = jarras[k][1]-jarras[k][0]
                            jarras[k][1] = jarras[k][0]
                            #la jarra de paso tiene el sobrante
                            jarras[i][1] = rest
                        #lo anade a la lista de sucesores
                        sucesores.append(copy.deepcopy(jarras))
                    #regresa su valor a la jarra paa poder seguir creando edos
                    jarras[i][1] = tmp
                    jarras[k][1] = tmp2
    print sucesores
    return sucesores


def value(jarras):
    #   para mi heuristica: devuelvo el valor de la jarra que tiene menos galones y eso
    #   es la evaluacion a mi estado
    menor = jarras[0][1]
    for i in range(3):
        if jarras[i][1] < menor and jarras[i][1]>0:
            menor = jarras[i][1]
    return menor


def main():
    """
    Resolver el problema de las jarras:
        Selecciono como mejor estado aquel en el que almenos una jarra
        tiene cerca de 1 galon.
    """
    edoact = edoinit()

    print("Edo Inicial: ", edoact)

    best = edoact

    while 1:
        # generar sucesores de este edo
        suc = fn_sucesores(edoact)

        # seleccionar mejor sucesor (el de menor evaluacion)
        for i in range(len(suc)):
            if value(suc[i]) < value(edoact):
                best = suc[i]

        # si ya no hubo mejora se sale
        if value(edoact) == value(best) or value(edoact) == 1:
            print "Ya no hay mejor edo."
            break
        elif value(best) < value(edoact):
            edoact = best
            print("Nuevo mejor: ", edoact)

    print("El mejor edo: ", edoact)

if __name__ == "__main__":
    main()
