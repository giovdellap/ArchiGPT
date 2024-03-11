local json = require("json")
local products = {}

local productList

function products:fetchProdutlist(callBack)
  local callBack = callBack
  local function networkListener( event )

    if ( event.isError ) then
      print( "Network error in fetching products list: ", event.response )
    else
      print("(((((((((((((((((((((productList)))))))))))))))))))))")
      print(productList)
      productList = json.decode(event.response)
    end
    if callBack then
      callBack(productList)
    end
  end

  network.request( urls.getProductList, "GET", networkListener )
end

function products:getProductList()
  return productList
end

return products
