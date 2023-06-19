import openai
from API_key import api_key
import base64
import sys

#gardentype object position

openai.api_key = api_key

args = sys.argv[1:]
prompt = "a top down view of a " + args[0] + " garden containing a " + args[1]
filename = "src/main/kotlin/furhatos/app/furgui/images/" + args[2] + ".png"

print("Creating tile\n")
response = openai.Image.create(
  prompt=prompt,
  response_format="b64_json",
  n=1,
  size="256x256"
)

with open(filename, "wb") as f:
    f.write(base64.b64decode(response["data"][0]["b64_json"]))