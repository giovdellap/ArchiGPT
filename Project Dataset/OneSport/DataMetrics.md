# SET LIST:
- SET 1: AUTH [1, 2, 3, 4, 5, 6]

- SET 2: EXTERNAL API NEWS [7, 8]

- SET 3: NEWS [9, 10, 15] / SET 2

- SET 4: EXTERNAL API TICKETS [11, 12]

- SET 5: TICKETS [13, 14, 15] / SET 4

- SET 6: SOCIAL [16, 17, 18]

- NO ASSOCIATION: []

n_cl = 4
n_ set = 6
n_c = 3 => r3 = 75
n_c = 4, 5, 6 => r3 = 100
n_c = 7 => 82

[
    {
        set_id: 1
        set_name: auth,
        user_stories: [1, 2, 3, 4, 5, 6],
        links: []
    },
    {
        set_id: 2
        set_name: external API news,
        user_stories: [7, 8],
        links: [3, 4]
    },
    {
        set_id: 3
        set_name: news,
        user_stories: [9, 10, 15],
        links: [2]
    },
    {
        set_id: 4
        set_name: external API tickets,
        user_stories: [11, 12],
        links: [2, 5]
    },
    {
        set_id: 5
        set_name: tickets,
        user_stories: [13, 14, 15],
        links: [4]
    },
    {
        set_id: 6
        set_name: social,
        user_stories: [16, 17, 18],
        links: []
    },
]

