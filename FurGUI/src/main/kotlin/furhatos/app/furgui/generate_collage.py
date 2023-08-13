import openai
from API_key import api_key
import base64
from PIL import Image
from os.path import isfile
import sys

openai.api_key = api_key
args = sys.argv[1:]
prompt = "a top down view of a " + args[0] + " garden"

image_dir = Path("src/main/kotlin/furhatos/app/furgui/images/")
files = ["{}{}.png".format(row, col) for row in "ABC" for col in "123"]

new_img = Image.new('RGBA', (1024,1024), (0, 0, 0, 0))
blank = Image.new('RGBA', (256,256), (0, 0, 0, 0))

img_index = 0
for i in range(0,1024,384):
    for j in range(0,1024,384):
        file_path = folder / files[img_index]
        if isfile(file_path):
            img = Image.open(file_path)
        else:
            img = blank
        new_img.paste(img, (j,i))
        img_index+=1

new_img.save(folder / "collage.png")

response = openai.Image.create_edit(
  image=open(folder / "collage.png", "rb"),
  mask=open(folder / "collage.png", "rb"),
  prompt=prompt,
  response_format="b64_json",
  n=1,
  size="1024x1024"
)

with open(folder / "image.png", "wb") as f:
    f.write(base64.b64decode(response["data"][0]["b64_json"]))

