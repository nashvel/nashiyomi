package com.example.tachiyomi.data

import com.example.tachiyomi.model.Chapter
import com.example.tachiyomi.model.Manga
import com.example.tachiyomi.model.MangaStatus
import com.example.tachiyomi.model.Page
import java.util.Date

/**
 * Sample data for testing the UI
 */
object SampleData {
    val sampleMangaList = listOf(
        Manga(
            id = 1,
            title = "One Piece",
            thumbnailUrl = "https://scontent.fmnl14-1.fna.fbcdn.net/v/t39.30808-6/487073432_1383030999488735_5954137077924683440_n.jpg?_nc_cat=100&ccb=1-7&_nc_sid=6ee11a&_nc_eui2=AeGHgNaekqnU3ihcWkg9HW0p4v9L1W4vM0fi_0vVbi8zR1MMf_ZmfVYaSMHbyGQGrAiPPLwV5KcFu4N2cZrlSGK6&_nc_ohc=ZGaQCO1pUNMQ7kNvwF0TtGS&_nc_oc=Adl4Zqn7b3dC7uWxieq8qAZrAsAunnTbDj0lTBh26IRwEamI3G3uT7rRme890wmMjIQ&_nc_zt=23&_nc_ht=scontent.fmnl14-1.fna&_nc_gid=hzzYjhUweGif5uAbUHGxlw&oh=00_AfLS72iJo7wpYYBNhNUJbd28AXqd0ZtttsW4V5Njv1_g4Q&oe=6843AC04",
            description = "Nacht refuses to let anyone or anything stand in the way of his quest to become the king of all pirates.",
            author = "Eiichiro Oda",
            status = MangaStatus.ONGOING,
            genres = listOf("Action", "Adventure", "Comedy", "Fantasy", "Shounen"),
            isFavorite = true
        ),
        Manga(
            id = 2,
            title = "Naruto",
            thumbnailUrl = "https://i.pinimg.com/736x/e5/20/6f/e5206f30d75e2fda222cd3316ca3a835--naruto-kakashi-naruto-couples.jpg",
            description = "Naruto is a young shinobi with an incorrigible knack for mischief. He's got a wild sense of humor, but Naruto is completely serious about his mission to be the world's greatest ninja!",
            author = "Masashi Kishimoto",
            status = MangaStatus.COMPLETED,
            genres = listOf("Action", "Adventure", "Fantasy", "Shounen"),
            isFavorite = true
        ),
        Manga(
            id = 3,
            title = "Demon Slayer",
            thumbnailUrl = "https://media4.giphy.com/media/v1.Y2lkPTc5MGI3NjExNjRmN2VoYm5wMWlhajgxb2M1M3I3Ym5sYWEzbDcycDBuNTAxaW1rOCZlcD12MV9pbnRlcm5hbF9naWZfYnlfaWQmY3Q9Zw/DGsDLr9nyz2LkVgKFs/giphy.gif",
            description = "In Taisho-era Japan, Tanjiro Kamado is a kindhearted boy who makes a living selling charcoal. But his peaceful life is shattered when a demon slaughters his entire family.",
            author = "Koyoharu Gotouge",
            status = MangaStatus.COMPLETED,
            genres = listOf("Action", "Supernatural", "Historical", "Shounen"),
            isFavorite = false
        ),
        Manga(
            id = 4,
            title = "My Hero Academia",
            thumbnailUrl = "https://m.media-amazon.com/images/I/718PSGrfmAL._AC_UF1000,1000_QL80_.jpg",
            description = "What would the world be like if 80 percent of the population manifested superpowers called 'Quirks'? Heroes and villains would be battling it out everywhere!",
            author = "Kohei Horikoshi",
            status = MangaStatus.ONGOING,
            genres = listOf("Action", "Superhero", "School Life", "Shounen"),
            isFavorite = false
        ),
        Manga(
            id = 5,
            title = "Jujutsu Kaisen",
            thumbnailUrl = "https://fictionhorizon.com/wp-content/uploads/2023/09/GojoX-768x432.jpg",
            description = "Yuji Itadori is resolved to save the world from cursed demons, but he soon learns that the best way to do it is to slowly lose his humanity and become one himself.",
            author = "Gege Akutami",
            status = MangaStatus.ONGOING,
            genres = listOf("Action", "Supernatural", "School Life", "Shounen"),
            isFavorite = true
        ),
        Manga(
            id = 6,
            title = "Boku no Pico ",
            thumbnailUrl = "https://upload.wikimedia.org/wikipedia/en/f/f8/Bokunopico.jpg",
            description = "A blonde-haired boy who works a summer part-time job at his grandfather's bar. He is often portrayed swimming, usually naked or in a blue Speedo. He has worn girls' clothes ever since Tamotsu (Mokkun) gave some to him as a gift. Later, feeling hurt that Tamotsu would not define their relationship, he rebels by cutting his hair and running away from home, although later on they reconcile. The following summer, Pico meets Chico, who is swimming naked in a stream, while riding his bike. He soon becomes friends with Chico, who calls him Oniichan (big brother), and they form a romantic and sexual relationship. In his relationships with both Mokkun and Chico, he is the uke, though in the latter relationship this is somewhat reversible",
            author = "Yatabe Katsuyoshi",
            status = MangaStatus.ONGOING,
            genres = listOf("Action", "Comedy", "School Life", "Slice of Life"),
            isFavorite = true
        )
    )

    val sampleChapters = listOf(
        Chapter(
            id = 1,
            mangaId = 1,
            name = "Romance Dawn - The Dawn of the Adventure",
            number = 1f,
            dateUpload = Date(),
            read = true,
            pageCount = 54
        ),
        Chapter(
            id = 2,
            mangaId = 1,
            name = "They Call Him 'Straw Hat Luffy'",
            number = 2f,
            dateUpload = Date(),
            read = true,
            pageCount = 22
        ),
        Chapter(
            id = 3,
            mangaId = 1,
            name = "Morgan versus Luffy",
            number = 3f,
            dateUpload = Date(),
            read = false,
            pageCount = 20
        ),
        Chapter(
            id = 4,
            mangaId = 2,
            name = "Naruto Uzumaki",
            number = 1f,
            dateUpload = Date(),
            read = true,
            pageCount = 50
        ),
        Chapter(
            id = 5,
            mangaId = 2,
            name = "Konohamaru!",
            number = 2f,
            dateUpload = Date(),
            read = false,
            pageCount = 24
        ),
        Chapter(
            id = 6,
            mangaId = 6,
            name = "The Beginning",
            number = 1f,
            dateUpload = Date(),
            read = false,
            pageCount = 25
        ),
        Chapter(
            id = 7,
            mangaId = 6,
            name = "The Power Within",
            number = 2f,
            dateUpload = Date(),
            read = false,
            pageCount = 22
        )
    )

    val samplePages = listOf(
        Page(0, "https://via.placeholder.com/800x1200.png?text=Page+1"),
        Page(1, "https://via.placeholder.com/800x1200.png?text=Page+2"),
        Page(2, "https://via.placeholder.com/800x1200.png?text=Page+3"),
        Page(3, "https://via.placeholder.com/800x1200.png?text=Page+4"),
        Page(4, "https://via.placeholder.com/800x1200.png?text=Page+5")
    )
}
