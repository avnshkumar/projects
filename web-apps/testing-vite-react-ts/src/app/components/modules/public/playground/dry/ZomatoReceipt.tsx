import ZomataoTC from "../../../../../designSystem/images/zomato_tc.png";
import ZomatoFssai from "../../../../../designSystem/images/zomato_fssai.png";

const ZomatoReceipt = () => {
  // const data_6_2 = {
  //     orderID: "6589286014",
  //     orderTime: "06 February 2025, 09:21 PM",
  //     customerName: "Avinash Kumar",
  //     deliveryAddress: "Davanam Sarovar Portico Suites Premium Hotel, Davanam Plaza, Opposite Madiwala Police Station, Hosur Main Road, Bengaluru, Karnataka",
  //     restaurantName: "Magnolia Bakery",
  //     restaurantAddress: "788, JK Plaza, 12th Main Road, Indiranagar, Bangalore",
  //     deliveryPartnerName: "M Ram Kumar Yadav",
  //     items:[
  //         {
  //             name: "Double Fudge Brownie",
  //             quantity: 2,
  //             unitPrice: "250",
  //         },
  //         {
  //             name:"Classic Tres Leches",
  //             quantity: 2,
  //             unitPrice: 560
  //         },
  //         {
  //             name:"German Chocolate Cake Slice",
  //             quantity: 1,
  //             unitPrice: 470
  //         },
  //         {
  //             name:"Vanilla Bean Cheesecake",
  //             quantity: 2,
  //             unitPrice: 460
  //         },
  //     ],
  // }
  //
  // const data_6_1 = {
  //     orderID: "6585052433",
  //     orderTime: "06 February 2025, 09:07 PM",
  //     customerName: "Avinash Kumar",
  //     deliveryAddress: "Davanam Sarovar Portico Suites Premium Hotel, Davanam Plaza, Opposite Madiwala Police Station, Hosur Main Road, Bengaluru, Karnataka",
  //     restaurantName: "Daily Sushi",
  //     restaurantAddress: "16, 1st Floor, 1st A Cross, Koramangala 5th Block, Bangalore",
  //     deliveryPartnerName: "Ajit Kumar",
  //     items: [
  //         {
  //             name:"Nigiri Sushi Combo Set - A (7 Pcs Mixed Sushi Set)",
  //             quantity: 1,
  //             unitPrice: 740
  //         }
  //     ]
  //
  // }

  // const data = {
  //   orderID: "6575647147",
  //   orderTime: "05 February 2025, 08:45 PM",
  //   customerName: "Avinash Kumar",
  //   deliveryAddress:
  //     "Davanam Sarovar Portico Suites Premium Hotel, Davanam Plaza, Opposite Madiwala Police Station, Hosur Main Road, Bengaluru, Karnataka",
  //   restaurantName: "Matka Biryani - Slow Cooked",
  //   restaurantAddress:
  //     "120-P, A3, Santosh Towers, KIADB Industrial Area, EPIP Zone, Whitefield, Bangalore",
  //   deliveryPartnerName: "Shravan Kumar",
  //   items: [
  //     {
  //       name: "1 KG Bhuna Chicken Matka Biryani",
  //       quantity: 1,
  //       unitPrice: "1374",
  //     },
  //     {
  //       name: "Salan",
  //       quantity: 1,
  //       unitPrice: "85",
  //     },
  //     {
  //       name: "Matka Phirni",
  //       quantity: 1,
  //       unitPrice: "520",
  //     },
  //     {
  //       name: "Gulab Jamun",
  //       quantity: 4,
  //       unitPrice: "120",
  //     },
  //   ],
  // };

  type OrderItem = {
    name: string;
    quantity: number;
    unitPrice: string;
  };

  type OrderData = {
    orderID: string;
    orderTime: string;
    customerName: string;
    deliveryAddress: string;
    restaurantName: string;
    restaurantAddress: string;
    deliveryPartnerName: string;
    items: OrderItem[];
  };

  const data: OrderData = {
    orderID: "6575647147",
    orderTime: "05 February 2025, 08:45 PM",
    customerName: "Avinash Kumar",
    deliveryAddress:
      "Davanam Sarovar Portico Suites Premium Hotel, Davanam Plaza, Opposite Madiwala Police Station, Hosur Main Road, Bengaluru, Karnataka",
    restaurantName: "Matka Biryani - Slow Cooked",
    restaurantAddress:
      "120-P, A3, Santosh Towers, KIADB Industrial Area, EPIP Zone, Whitefield, Bangalore",
    deliveryPartnerName: "Shravan Kumar",
    items: [
      {
        name: "1 KG Bhuna Chicken Matka Biryani",
        quantity: 1,
        unitPrice: "1374",
      },
      {
        name: "Salan",
        quantity: 1,
        unitPrice: "85",
      },
      {
        name: "Matka Phirni",
        quantity: 1,
        unitPrice: "520",
      },
      {
        name: "Gulab Jamun",
        quantity: 4,
        unitPrice: "120",
      },
    ],
  };

  // const data_4_1 = {
  //     orderID: "6555291198",
  //     orderTime: "04 February 2025, 10:28 PM",
  //     customerName: "Avinash Kumar",
  //     deliveryAddress: "Davanam Sarovar Portico Suites Premium Hotel, Davanam Plaza, Opposite Madiwala Police Station, Hosur Main Road, Bengaluru, Karnataka",
  //     restaurantName: "The Black Pearl",
  //     restaurantAddress: "6th Floor, Swamy Lagoto Building, 7, Outer Ring Rd, above Kia Motors, Kadubeesanahalli, Marathahalli, Bengaluru",
  //     deliveryPartnerName: "Amora K S",
  //     items: [
  //         {
  //             name: "Pirate BBQ Box ( Non-veg )",
  //             quantity: 1,
  //             unitPrice: "1399"
  //         },
  //         {
  //             name: "Dhum Jhinga Anari",
  //             quantity: 1,
  //             unitPrice: "499"
  //         },
  //     ]
  //
  // }
  //
  // const data_4_2 = {
  //     orderID : "6555432109",
  //     orderTime: "04 February 2025, 10:42 PM",
  //     customerName: "Avinash Kumar",
  //     deliveryAddress: "Davanam Sarovar Portico Suites Premium Hotel, Davanam Plaza, Opposite Madiwala Police Station, Hosur Main Road, Bengaluru, Karnataka",
  //     restaurantName: "Magnolia Bakery",
  //     restaurantAddress: "788, JK Plaza, 12th Main Road, Indiranagar, Bangalore",
  //     deliveryPartnerName: "M Ashutosh Sharma",
  //     items: [
  //         {
  //             name: "Classic Tres Leches",
  //             quantity: 2,
  //             unitPrice: 560
  //         },
  //         {
  //             name: "Caramel Pecan Cheesecake",
  //             quantity: 1,
  //             unitPrice: 340
  //         }
  //     ]
  // }

  // const data_8_1 = {
  //     orderID: "6619850092",
  //     orderTime: "08 February 2025, 10:02 PM",
  //     customerName: "Avinash Kumar",
  //     deliveryAddress: "Davanam Sarovar Portico Suites Premium Hotel, Davanam Plaza, Opposite Madiwala Police Station, Hosur Main Road, Bengaluru, Karnataka",
  //     restaurantName: "ITC Gardenia - Gourment Couch",
  //     restaurantAddress: "1, Residency Road, Bangalore",
  //     deliveryPartnerName: "Shivam Kumar",
  //     items: [
  //         {
  //             name:"Dum Pukht Biryani",
  //             quantity: 1,
  //             unitPrice: 1825,
  //         },
  //         {
  //             name: "Murgh Angaar",
  //             quantity: 1,
  //             unitPrice: 1550,
  //         },
  //     ]
  // }

  const itemTotal = data.items.reduce((total, item) => {
    total += item.quantity * parseInt(item.unitPrice);
    return total;
  }, 0);
  const taxes = (itemTotal * 5) / 100;
  const delivery = 42;
  const restaurantPackaging = 32 + Math.floor(itemTotal / 100);
  const platform = 10;
  const final = itemTotal + taxes + delivery + restaurantPackaging + platform;

  return (
    <div className='flex flex-col w-[800px] p-24 bg-[#ffffff]'>
      <div className='mb-5 text-xl font-bold'>Zomato Food Order: Summary and Receipt</div>
      <div className='flex flex-col text-sm space-y-1 mb-4'>
        <div className='flex justify-between w-full'>
          <div className='w-[250px]'>Order ID:</div>
          <div className='w-full '>
            <div className='text-[#9c9c9c]'>{data.orderID}</div>
          </div>
        </div>

        <div className='flex justify-between w-full'>
          <div className='w-[250px]'>Order Time:</div>
          <div className='w-full '>
            <div className='text-[#9c9c9c]'>{data.orderTime}</div>
          </div>
        </div>

        <div className='flex justify-between w-full'>
          <div className='w-[250px]'>Customer Name:</div>
          <div className='w-full '>
            <div className='text-[#9c9c9c]'>{data.customerName}</div>
          </div>
        </div>

        <div className='flex justify-between w-full'>
          <div className='w-[250px]'>Delivery Address:</div>
          <div className='w-full '>
            <div className='text-[#9c9c9c]'>{data.deliveryAddress}</div>
          </div>
        </div>

        <div className='flex justify-between w-full'>
          <div className='w-[250px]'>Restaurant Name:</div>
          <div className='w-full '>
            <div className='text-[#9c9c9c]'>{data.restaurantName}</div>
          </div>
        </div>

        <div className='flex justify-between w-full'>
          <div className='w-[250px]'>Restaurant Address:</div>
          <div className='w-full '>
            <div className='text-[#9c9c9c]'>{data.restaurantAddress}</div>
          </div>
        </div>

        <div className='flex justify-between w-full'>
          <div className='w-[250px]'>Delivery partner's Name:</div>
          <div className='w-full '>
            <div className='text-[#9c9c9c]'>{data.deliveryPartnerName}</div>
          </div>
        </div>
      </div>

      <div className='flex flex-col '>
        <div className='flex bg-[#acacac] text-[#ffffff] text-sm font-bold  py-1 justify-between px-2'>
          <div className='w-[350px]'>Item</div>
          <div className='w-[90px]'>Quantity</div>
          <div className='w-[100px]'>Unit Price</div>
          <div className='w-[100px]'>Total Price</div>
        </div>

        {data.items.map((item, index) => (
          <div
            className='flex text-[#000000]  text-sm mx-2  py-1 justify-between items-center'
            key={index}
          >
            <div className='w-[320px] text-sm'>{item.name}</div>
            <div className='flex w-[70px] justify-center'>{item.quantity}</div>
            <div className='flex w-[100px] justify-end'>₹{item.unitPrice}</div>
            <div className='flex w-[100px] justify-end'>
              ₹{item.quantity * parseInt(item.unitPrice)}
            </div>
          </div>
        ))}
      </div>

      <div className='h-0.5 bg-[#acacac]'></div>

      <div className='flex flex-col'>
        <div className='flex text-[#000000]  text-sm mx-2  py-1  justify-end items-center'>
          <div className='flex justify-end'>Taxes</div>
          <div className='flex w-[100px] justify-end'>₹{taxes}</div>
        </div>

        <div className='flex text-[#000000]  text-sm mx-2  py-1  justify-end items-center'>
          <div className='flex justify-end'>Delivery charge subtotal</div>
          <div className='flex w-[100px] justify-end'>₹{delivery}</div>
        </div>

        <div className='flex text-[#000000]  text-sm mx-2  py-1  justify-end items-center'>
          <div className='flex justify-end'>Restaurant Packaging Charges</div>
          <div className='flex w-[100px] justify-end'>₹{restaurantPackaging}</div>
        </div>

        <div className='flex text-[#000000]  text-sm mx-2  py-1  justify-end items-center'>
          <div className='flex justify-end'>Platform fee</div>
          <div className='flex w-[100px] justify-end'>₹{platform}</div>
        </div>
      </div>

      <div className='flex flex-col  mb-6'>
        <div className='flex bg-[#cccccc] text-[#000000] text-sm font-bold  py-1  justify-end items-center px-2'>
          <div className='flex justify-end'>Total</div>
          <div className='flex w-[100px] justify-end'>₹{final}</div>
        </div>
      </div>

      <img src={ZomataoTC} alt='zomato'></img>
      <div className='flex justify-end'>
        <img src={ZomatoFssai} alt='zomato-fssai' height={200} width={200}></img>
      </div>
    </div>
  );
};

export default ZomatoReceipt;
