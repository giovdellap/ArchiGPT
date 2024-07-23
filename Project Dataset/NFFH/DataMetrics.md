# SET LIST:
set = [
    {
        set_id: 1
        set_name: auth client,
        user_stories: [1, 2, 3, 4]
    },
    {
        set_id: 2
        set_name: auth admin,
        user_stories: [22, 23, 24]
    },
    {
        set_id: 3
        set_name: auth farmer,
        user_stories: [17, 18, 19, 20]
    },
    {
        set_id: 4
        set_name: farmer,
        user_stories: [21, 32]
    },
    {
        set_id: 5
        set_name: client,
        user_stories: [5, 25, 27, 28, 29]
    },
    {
        set_id: 6
        set_name: cart,
        user_stories: [9, 10, 11]
    },
    {
        set_id: 7
        set_name: frontend,
        user_stories: [13]
    },
    {
        set_id: 8
        set_name: products,
        user_stories: [6, 14, 15, 16, 8, 33, 34, 36, 37, 38]
    },
    {
        set_id: 9
        set_name: area,
        user_stories: [7, 30, 35, 31]
    },
    {
        set_id: 10
        set_name: order,
        user_stories: [12, 26]
    },
]

cluster = [
    {
        cluster_id: 1,
        cluster_name: auth,
        sets: [1, 2, 3]
    },
        {
        cluster_id: 2,
        cluster_name: farmer,
        sets: [4, 9]
    },
    {
        cluster_id: 3,
        cluster_name: frontend,
        sets: [6, 7]
    },
]



- SET 3: CLIENT-RELATED ACTIONS [5, 25, 27, 28, 29]

- SET 5: CART [9, 10, 11]

- SET 6: ORDER [12, 26]

- SET 7: FRONTEND [ 13 ]

- SET 8: PRODUCTS [6, 14, 15, 16, 8, 33, 34, 36, 37, 38]
- SET 11: AREA-RELATED ACTIONS [7, 30, 35, 31]

- NO ASSOCIATION: []

[
    {
        set_id: 1
        set_name: auth,
        user_stories: [1, 2, 3, 4, 5, 6],
    },
]