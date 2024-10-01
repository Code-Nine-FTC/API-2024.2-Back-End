from selenium import webdriver
from selenium.webdriver.chrome.service import Service
from selenium.webdriver.common.by import By
from selenium.webdriver.support.ui import WebDriverWait
from selenium.webdriver.support import expected_conditions as EC
import time
import json

def enterProject(link):
    link.click()
    WebDriverWait(driver, 10).until(EC.presence_of_element_located((By.CLASS_NAME, "col-sm-9"))) # Espera o elemento carregar
    project_data = driver.find_elements(By.CLASS_NAME, "col-sm-9") # Seleciona o elemento a ser pego 
    data = [element.text for element in project_data] # Pega os dados da página 
    
    # Estrutura o dicionário com os dados raspados
    project_info = {
        'Referência do projeto': data[0] if len(data) > 0 else '',
        'Empresa': data[1] if len(data) > 1 else '',
        'Objeto': data[2] if len(data) > 2 else '',
        'Descrição': data[3] if len(data) > 3 else '',
        'Coordenador': data[4] if len(data) > 4 else '',
        'Valor do projeto': data[5] if len(data) > 5 else '',
        'Data de início': data[6] if len(data) > 6 else '',
        'Data de término': data[7] if len(data) > 7 else ''
    }
    
    return project_info

try:
    # A classe Service é utilizada para iniciar uma instancia do Chrome Driver
    service = Service()

    # webdriver.ChromeOptions é usada para definir a preferencia para o browser Chrome
    options = webdriver.ChromeOptions()

    # Inicia-se a instancia do Chrome Webdriver com as definidas options e service 
    driver = webdriver.Chrome(service=service, options=options)

    url = "https://fapg.org.br/projetos/index.php?act=filter&projeto=&coordenador=&inicio=&termino=&classificacao=&filter=all"
    driver.get(url)

    links = driver.find_elements(By.TAG_NAME, "a")
    all_projects_data = []

    for i in range(len(links)):
        project_info = enterProject(links[i])
        all_projects_data.append(project_info)
        driver.back()
        WebDriverWait(driver, 10).until(EC.presence_of_element_located((By.TAG_NAME, "a")))
        time.sleep(2) # Ele da um tempinho de 2 sec pra nao sobrecarregar

    # Converte os dados raspados para JSON e salva em um arquivo
    with open('projects_data.json', 'w', encoding='utf-8') as f:
        json.dump(all_projects_data, f, ensure_ascii=False, indent=4)

finally:
    driver.quit()