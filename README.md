# Built by Pixel NewsUK Android Task Submission

Thanks for taking the time to review my submission for the Built by Pixel Android task.

This was a fun project that allowed me to solidify some best practices and hopefully show off a lot of my coding ability.

I attempted to write clean, SOLID code, demonstrating separation of concerns, clean architecture, and sound MVVM design decisions.

The project is scalable; I included a navigation component in case more screens need to be added.

I attempted to handle errors gracefully, and used material theming to (hopefully) make a pleasant user experience.

---

## How I used AI

- Creating data classes (from the given format of API response)
- For formatting this Readme
- Helping to debug some testing issues
- Giving me colours for the material theme based on StackOverflow's colour scheme
- Some dependency injection tips for getting the datastore

---

## Improvements

Given more time, there are some cool features I would have liked to add:

- Refactor to not use hardcoded dimensions (currently not very responsive for tablets; hardcoded dimension values shouldn't be used in a production app)
- Add medals to a user card, and maybe display more complete info on a new page (or bottomModal) when the user is clicked on
- The ability to go through more pages of results (Paging 3 library or a bespoke solution perhaps)
- Page size change, allow for more than 20 users (currently hardcoded based on specification)
- Pull down to refresh (not needed for this implementation, but in other scenarios would be useful)
- API call for follow/unfollow (would require some refactoring)
- More tests
- Not all strings are extracted as resources, so not good for translating

---

## Thanks again!
