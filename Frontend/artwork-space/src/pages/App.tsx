import React from 'react';
import CustomLink from "../components/CustomLink";
function App() {
  return (
    <div className={"flex flex-row"}>
        <div className={"w-1/2 mx-14 flex flex-col gap-8 mt-36"}>
            <div className="w-2/3">
                <p className={"text-3xl font-bold"}>Explore unique artworks</p>
                <p className={"text-gray-400"}>Each piece tells a unique story, offering a glimpse into the boundless possibilities of the canvas.</p>
            </div>
            <CustomLink title={"See all artworks"} url={"artworks"}/>
        </div>
      <img src={"./images/flowers.jpg"} alt={"Flowers painting"} className={"w-1/2"}/>
    </div>
  );
}

export default App;
