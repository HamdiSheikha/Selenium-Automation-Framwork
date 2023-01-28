Feature: Monstarlab test Suite

  Scenario Outline: Verify carousel number <index> items uniqueness
    Given I scroll "<index>" times
    When I check the number of items in the "<carousel_selector>" list for "<index>" carousel will be >= "15"
    Then I verify the items in the "<carousel_selector>" for "<index>" carousel are unique

    Examples:
      | carousel_selector         | index |
      | large_half_width_carousel | 1     |
      | large_half_width_carousel | 2     |
      | large_half_width_carousel | 3     |
      | large_half_width_carousel | 5     |
      | large_full_width_carousel | 4     |
      | large_full_width_carousel | 6     |
      | large_full_width_carousel | 9     |
      | small_full_width_carousel | 13    |
