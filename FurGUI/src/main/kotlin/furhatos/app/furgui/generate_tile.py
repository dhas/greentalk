import openai
from API_key import api_key
import base64
import sys
from pathlib import Path

#gardentype object position

openai.api_key = api_key

args = sys.argv[1:]
prompt = "a top down view of a " + args[0] + " garden containing a " + args[1]
image_dir = Path("src/main/kotlin/furhatos/app/furgui/images/")
image_dir.mkdir(parents=True)
filename = image_dir / "{}.png".format(args[2])

print("Creating tile\n")
response = openai.Image.create(
  prompt=prompt,
  response_format="b64_json",
  n=1,
  size="256x256"
)

with open(filename, "wb") as f:
    f.write(base64.b64decode(response["data"][0]["b64_json"]))